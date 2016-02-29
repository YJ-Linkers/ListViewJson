package com.linkcreators.listviewjson.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class MyListView extends ListView {
	private OnScrollListener mScrollListener; // user's scroll listener

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyListView(Context context) {
		super(context);
	}

	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * scrollView嵌套后高度显示问题解决
	 */
//	@Override
//	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//
//		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
//		Log.v("expandSpec", expandSpec + "");
//		super.onMeasure(widthMeasureSpec, expandSpec);
//	}

}
