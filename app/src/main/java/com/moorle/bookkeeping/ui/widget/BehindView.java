package com.moorle.bookkeeping.ui.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class BehindView extends ViewGroup {

	private AboveView mAboveView;
	private View mContent;
	
	private int mHeight;
	
	public BehindView(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = getDefaultSize(0, widthMeasureSpec);
		int height = getDefaultSize(0, heightMeasureSpec);
		setMeasuredDimension(width, height);
		final int contentWidth = getChildMeasureSpec(widthMeasureSpec, 0, width);
		final int contentHeight = getChildMeasureSpec(heightMeasureSpec, 0, mHeight);
		mContent.measure(contentWidth, contentHeight);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		final int width = r - l;
		mContent.layout(0, 0, width, mHeight);
	}

    public void setAboveView(AboveView v) {
		mAboveView = v;
	}
	
	public void setContent(View v) {
		if (mContent != null)
			removeView(mContent);
		mContent = v;
		addView(mContent);
	}
	
	public int getBehindHeight() {
		return mContent.getHeight();
	}
	
	public void setBehindHeight(int h) {
		mHeight = h;
		requestLayout();
	}

}
