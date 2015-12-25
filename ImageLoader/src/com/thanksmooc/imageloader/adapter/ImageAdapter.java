package com.thanksmooc.imageloader.adapter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.image.demo.R;
import com.thanksmooc.imageloader.util.ImageLoader;
import com.thanksmooc.imageloader.util.ImageLoader.Type;

public class ImageAdapter extends BaseAdapter {
	
	private static Set<String> mSelectedImg = new HashSet<String>();
	private String mDirPath;
	private List<String> mImagePaths;
	private LayoutInflater mInflater;
	
	public ImageAdapter(Context context, List<String> mDatas, String dirPath) {
		this.mDirPath = dirPath;
		this.mImagePaths = mDatas;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mImagePaths.size();
	}

	@Override
	public Object getItem(int position) {
		return mImagePaths.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final ViewHolder viewHolder;// 这里不能为null, 不然不能在下面进行两次初始化
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_gridview, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.mImg = (ImageView) convertView.findViewById(R.id.id_item_image);
			viewHolder.mSelect = (ImageView) convertView.findViewById(R.id.id_item_select);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		// 重置状态
		viewHolder.mImg.setImageResource(R.drawable.pictures_no);
		viewHolder.mSelect.setImageResource(R.drawable.picture_unselected);
		viewHolder.mImg.setColorFilter(null);
		
		ImageLoader.getInstance(3, Type.LIFO).loadImage(mDirPath + "/" +mImagePaths.get(position), viewHolder.mImg);
		
		final String filePath = mDirPath+ "/" + mImagePaths.get(position);
		
		viewHolder.mImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// 已经被选择
				if (mSelectedImg.contains(filePath)) {
					
					mSelectedImg.remove(filePath);
					viewHolder.mImg.setColorFilter(null);
					viewHolder.mSelect.setImageResource(R.drawable.picture_unselected);
				} else {// 未被选择
					
					mSelectedImg.add(filePath);
					viewHolder.mImg.setColorFilter(Color.parseColor("#77000000"));
					viewHolder.mSelect.setImageResource(R.drawable.pictures_selected);
				}
				// 不用notifyDataSetChanged()通知更新，因为屏幕会闪
			}
		});
		
		if (mSelectedImg.contains(filePath)) {
			
			viewHolder.mImg.setColorFilter(Color.parseColor("#77000000"));
			viewHolder.mSelect.setImageResource(R.drawable.pictures_selected);
		}
		
		return convertView;
	}
	
	private class ViewHolder {
		ImageView mImg, mSelect;
	}
}
