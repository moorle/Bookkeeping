package com.moorle.bookkeeping.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class AboveView extends ViewGroup {

	private static final Interpolator sInterpolator = new Interpolator() {
		public float getInterpolation(float t) {
			t -= 1.0f;
			return t * t * t * t * t + 1.0f;
		}
	};
	
	private static final int MAX_SETTLE_DURATION = 600; // ms
	
	private View mContent;
	
	private BehindView mBehindView;
	
	private int mCurItem;
	private Scroller mScroller;
	
	private boolean mScrolling;
	
	public AboveView(Context context) {
		this(context, null);
	}

	public AboveView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initAboveView();
	}

	void initAboveView() {
		final Context context = getContext();
		mScroller = new Scroller(context, sInterpolator);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int width = getDefaultSize(0, widthMeasureSpec);
		int height = getDefaultSize(0, heightMeasureSpec);
		setMeasuredDimension(width, height);

		final int contentWidth = getChildMeasureSpec(widthMeasureSpec, 0, width);
		final int contentHeight = getChildMeasureSpec(heightMeasureSpec, 0, height);
		mContent.measure(contentWidth, contentHeight);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		final int width = r - l;
		final int height = b - t;
		mContent.layout(0, 0, width, height);
	}
	
	// We want the duration of the page snap animation to be influenced by the distance that
	// the screen has to travel, however, we don't want this duration to be effected in a
	// purely linear fashion. Instead, we use this method to moderate the effect that the distance
	// of travel has on the overall snap duration.
	float distanceInfluenceForSnapDuration(float f) {
		f -= 0.5f; // center the values about 0.
		f *= 0.3f * Math.PI / 2.0f;
		return (float) FloatMath.sin(f);
	}
	
	public void setBehindView(BehindView v) {
		mBehindView = v;
	}
	
	public void setContent(View v) {
		if (mContent != null) 
			this.removeView(mContent);
		mContent = v;
		addView(mContent);
	}
	
	public void setCurrentItem(int item) {
		setCurrentItemInternal(item, true, false);
	}

	public void setCurrentItem(int item, boolean smoothScroll) {
		setCurrentItemInternal(item, smoothScroll, false);
	}
	
	void setCurrentItemInternal(int item, boolean smoothScroll, boolean always) {
		setCurrentItemInternal(item, smoothScroll, always, 0);
	}
	
	void setCurrentItemInternal(int item, boolean smoothScroll, boolean always, int velocity) {
		if (!always && mCurItem == item) {
			return;
		}

		mCurItem = item;
		final int destY = getDestScrollY(mCurItem);

		if (smoothScroll) {
			smoothScrollTo(0, destY, velocity);
		} else {
			completeScroll();
			scrollTo(0, destY);
		}
	}
	
	public int getDestScrollY(int page) {
		switch (page) {
		case 0:
			return mContent.getTop() - getBehindHeight();
		case 1:
			return mContent.getTop();
		}
		return 0;
	}

	public int getCurrentItem() {
		return mCurItem;
	}
	
	void smoothScrollTo(int x, int y) {
		smoothScrollTo(x, y, 0);
	}

	/**
	 * Like {@link android.view.View#scrollBy}, but scroll smoothly instead of immediately.
	 *
	 * @param x the number of pixels to scroll by on the X axis
	 * @param y the number of pixels to scroll by on the Y axis
	 * @param velocity the velocity associated with a fling, if applicable. (0 otherwise)
	 */
	void smoothScrollTo(int x, int y, int velocity) {
		if (getChildCount() == 0) {
			// Nothing to do.
			return;
		}
		int sx = getScrollX();
		int sy = getScrollY();
		int dx = x - sx;
		int dy = y - sy;
		if (dx == 0 && dy == 0) {
			completeScroll();
			return;
		}

		mScrolling = true;

		final int height = getBehindHeight();
		final int halfHeight = height / 2;
		final float distanceRatio = Math.min(1f, 1.0f * Math.abs(dy) / height);
		final float distance = halfHeight + halfHeight *
				distanceInfluenceForSnapDuration(distanceRatio);

		int duration = 0;
		velocity = Math.abs(velocity);
		if (velocity > 0) {
			duration = 4 * Math.round(1000 * Math.abs(distance / velocity));
		} else {
			final float pageDelta = (float) Math.abs(dy) / height;
			duration = (int) ((pageDelta + 1) * 100);
			duration = MAX_SETTLE_DURATION;
		}
		duration = Math.min(duration, MAX_SETTLE_DURATION);

		mScroller.startScroll(sx, sy, dx, dy, duration);
		invalidate();
	}
	
	@Override
	public void computeScroll() {
		if (!mScroller.isFinished()) {
			if (mScroller.computeScrollOffset()) {
				int oldX = getScrollX();
				int oldY = getScrollY();
				int x = mScroller.getCurrX();
				int y = mScroller.getCurrY();

				if (oldX != x || oldY != y) {
					scrollTo(x, y);
					pageScrolled(x);
				}

				// Keep on drawing until the animation has finished.
				invalidate();
				return;
			}
		}

		// Done with scroll, clean up state.
		completeScroll();
	}

	private void completeScroll() {
		boolean needPopulate = mScrolling;
		if (needPopulate) {
			// Done with scroll, no longer want to cache view drawing.
			mScroller.abortAnimation();
			int oldX = getScrollX();
			int oldY = getScrollY();
			int x = mScroller.getCurrX();
			int y = mScroller.getCurrY();
			if (oldX != x || oldY != y) {
				scrollTo(x, y);
			}
		}
		mScrolling = false;
	}
	
	private void pageScrolled(int xpos) {
		final int widthWithMargin = getWidth();
		final int position = xpos / widthWithMargin;
		final int offsetPixels = xpos % widthWithMargin;
		final float offset = (float) offsetPixels / widthWithMargin;

		//onPageScrolled(position, offset, offsetPixels);
	}
	
	public void setBehindHeight(int h) {
		mBehindView.setBehindHeight(h);
	}
	
	public int getBehindHeight() {
		if (mBehindView == null) {
			return 0;
		} else {
			return mBehindView.getBehindHeight();
		}
	}

}
