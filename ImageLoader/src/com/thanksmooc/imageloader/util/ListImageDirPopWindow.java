package com.thanksmooc.imageloader.util;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.image.demo.R;
import com.thanksmooc.imageloader.bean.FolderBean;

public class ListImageDirPopWindow extends PopupWindow{
	private int mWidth;
	private int mHeight;
	private View mConVertView;
	private ListView mListView;
	private List<FolderBean> mDatas;
	
	// ListItem ����¼��Ľӿ�
	public interface OnDirSelectListener {
		void onSelected(FolderBean folderBean);
	}
	
	public OnDirSelectListener mDirSelectListener;
	
	public void setOnDirSelectListener (OnDirSelectListener mDirSelectListener) {
		this.mDirSelectListener = mDirSelectListener;
	}
	
	/**
	 * ����popWindow�Ŀ�Ⱥ͸߶�
	 * @param context
	 */
	public ListImageDirPopWindow(Context context, List<FolderBean> datas) {
		
		calWidthAndHeight(context);
		
		mConVertView = LayoutInflater.from(context).inflate(R.layout.pop_main, null);
		mDatas = datas;
		
		setContentView(mConVertView);
		setWidth(mWidth);
		setHeight(mHeight);
		
		setFocusable(true);
		setTouchable(true);
		setOutsideTouchable(true);
		setBackgroundDrawable(new BitmapDrawable());
		
		setTouchInterceptor(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					dismiss();
					return true;
				}
				return false;
			}
		});
		
		initView(context);
		initEvent();
	}

	private void initView(Context context) {
		mListView = (ListView) mConVertView.findViewById(R.id.id_list_dir);
		mListView.setAdapter(new ListDitAdapter(context, mDatas));
	}
	
	private void initEvent() {
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (mDirSelectListener != null) {
					mDirSelectListener.onSelected(mDatas.get(position));
				}
			}
			
		});
	}
	
	private class ListDitAdapter extends ArrayAdapter<FolderBean> {
		private LayoutInflater mInflater;
		private List<FolderBean> mDatas;

		public ListDitAdapter(Context context, List<FolderBean> objects) {
			super(context, 0, objects);
			
			mInflater = LayoutInflater.from(context);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewHolder viewHolder;
			if (convertView == null) {
				
				viewHolder = new ViewHolder();
				// TODO ��Inflateѧϰһ��
				convertView = mInflater.inflate(R.layout.item_of_pop_main, parent, false);
				viewHolder.mImage = (ImageView) convertView.findViewById(R.id.id_dir_item_image);
				viewHolder.mDirName = (TextView) convertView.findViewById(R.id.id_dir_item_name);
				viewHolder.mDirCount = (TextView) convertView.findViewById(R.id.id_dir_item_count);
				
				convertView.setTag(viewHolder);
			} else {
				
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			// ����һ��
			viewHolder.mImage.setImageResource(R.drawable.pictures_no);
			
			FolderBean folderBean = getItem(position);
			
			ImageLoader.getInstance().loadImage(folderBean.getFirstImgPath(), viewHolder.mImage);
			
			viewHolder.mDirName.setText(folderBean.getName());
			// getCount() ����Ϊ���֣���ȥ��Դ�ļ������ң���ת���ַ��Ļ�Ϊ����
			viewHolder.mDirCount.setText(folderBean.getCount() + "��");
			
			return convertView;
		}
		
		private class ViewHolder {
			ImageView mImage;
			TextView mDirName;
			TextView mDirCount;
		}
		
	}

	/**
	 * ����popWindow�Ŀ�Ⱥ͸߶�
	 * @param context
	 */
	private void calWidthAndHeight (Context context) {
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(displayMetrics);
		
		mWidth = displayMetrics.widthPixels;
		mHeight = (int)(displayMetrics.heightPixels * .7);
	}
	
}
