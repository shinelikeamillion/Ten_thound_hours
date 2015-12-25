package com.thanksmooc.imageloader;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.image.demo.R;
import com.thanksmooc.imageloader.adapter.ImageAdapter;
import com.thanksmooc.imageloader.bean.FolderBean;
import com.thanksmooc.imageloader.util.ListImageDirPopWindow;
import com.thanksmooc.imageloader.util.ListImageDirPopWindow.OnDirSelectListener;

public class MainActivity extends Activity {
	
	private GridView mGridView;
	private ImageAdapter mImageAdapter;
	private List<String> mImags;
	
	private RelativeLayout mBottomLy;
	
	private TextView mDirName, mDirCount;
	
	private File mCurrentDir;
	private int mMaxCount;
	
	private List<FolderBean> mFolderBeans = new ArrayList<FolderBean>();
	
	private ProgressDialog mProgressDialog;
	
	private static final int DATA_LOADED = 0x110;
	
	private ListImageDirPopWindow mDirPopWindow;
	
	private Handler mHandler = new Handler () {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == DATA_LOADED) {
				mProgressDialog.dismiss();
				
				// 绑定数据到view当中
				data2View();
				
				initDirPopWindow ();
			}
		}


	};
	
	private void initDirPopWindow() {
		mDirPopWindow = new ListImageDirPopWindow(this, mFolderBeans);
		
		mDirPopWindow.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				lightOn();
			}
		});
		
		mDirPopWindow.setOnDirSelectListener(new OnDirSelectListener() {
			
			@Override
			public void onSelected(FolderBean folderBean) {
				mCurrentDir = new File (folderBean.getDir());
				
				mImags = Arrays.asList(mCurrentDir.list(new FilenameFilter() {
					
					@Override
					public boolean accept(File dir, String filename) {
						if (filename.endsWith("jpg")
								|| filename.endsWith("png")
								|| filename.endsWith("jpeg")) {
							return true;
						}
						return false;
					}
				}));
				
				mImageAdapter = new ImageAdapter(MainActivity.this, mImags, mCurrentDir.getAbsolutePath());
				mGridView.setAdapter(mImageAdapter);
				
				mDirName.setText(folderBean.getName());
				mDirCount.setText(folderBean.getCount() + "张");
				mDirPopWindow.dismiss();
			}
		});
	}
	
	/**
	 * 内容区域变亮
	 */
	protected void lightOn() {
		WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
		layoutParams.alpha = 1.0f;
		getWindow().setAttributes(layoutParams);
	}

	private void data2View() {
		if (mCurrentDir == null) {
			Toast.makeText(this, "未扫描的图片:(", Toast.LENGTH_SHORT).show();
			return;
		}
		
		mImags = Arrays.asList(mCurrentDir.list());
		
		mImageAdapter = new ImageAdapter(this, mImags, mCurrentDir.getAbsolutePath());
		mGridView.setAdapter(mImageAdapter);
		
		mDirCount.setText(mMaxCount+"张");
		mDirName.setText(mCurrentDir.getName());
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
		initDatas();
		initEvent();
	}
	
	private void initView() {
		mGridView = (GridView) findViewById(R.id.id_gridView);
		mBottomLy = (RelativeLayout) findViewById(R.id.id_bottom_ly);
		mDirCount = (TextView) findViewById(R.id.id_dir_count);
		mDirName = (TextView) findViewById(R.id.id_dir_name);
	}
	
	/**
	 * 利用contentProvider扫描手机中所有的图片
	 */
	private void initDatas() {
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			Toast.makeText(this, "当前存储卡不可用", Toast.LENGTH_SHORT).show();
			return;
		}
		
		mProgressDialog = ProgressDialog.show(this, null, "正在加载");
		
		new Thread(){
			public void run() {
				Uri mImgUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				ContentResolver cr = MainActivity.this.getContentResolver();
				// or 后面没有加空格报错啊！！！
				Cursor cursor = cr.query(mImgUri, null, 
						MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
						new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
				
				Set<String> mDirPaths = new HashSet<String>();
				
				while (cursor.moveToNext()) {
					String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
					
					File parentFile = new File(path).getParentFile();
					if (parentFile == null)  continue;
					String dirPath = parentFile.getAbsolutePath();
					
					FolderBean folderBean = null;
					
					if (mDirPaths.contains(dirPath)) {
						continue;
					} else {
						mDirPaths.add(dirPath);
						folderBean = new FolderBean();
						folderBean.setDir(dirPath);
						folderBean.setFirstImgPath(path);
					}
					
					if (parentFile.list() == null) continue;
					
					int picSize = parentFile.list(new FilenameFilter() {
						
						@Override
						public boolean accept(File dir, String filename) {
							
							if (filename.endsWith(".jpg") ||
									filename.endsWith(".png") ||
									filename.endsWith(".jpeg")) {
								return true;
							}
							
							return false;
						}
					}).length;
					
					folderBean.setCount(picSize);
					mFolderBeans.add(folderBean);
					
					if (picSize > mMaxCount) {
						mMaxCount = picSize;
						mCurrentDir = parentFile;
					}
				}
				
				cursor.close();
				
				// 通知handler 扫描图片完成
				mHandler.sendEmptyMessage(DATA_LOADED);
			};
		}.start();
		
	}
	
	private void initEvent() {
		mBottomLy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mDirPopWindow.setAnimationStyle(R.style.dir_popwindow_anim);
				mDirPopWindow.showAsDropDown(mBottomLy, 0, 0);
				
				lightOff();
			}
		});
	}
	
	/**
	 * 内容背景变灰色
	 */
	protected void lightOff() {
		WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
		layoutParams.alpha = .3f;
		getWindow().setAttributes(layoutParams);
	}



}
