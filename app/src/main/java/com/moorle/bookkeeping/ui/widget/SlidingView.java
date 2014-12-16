package com.moorle.bookkeeping.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.moorle.bookkeeping.R;

public class SlidingView extends FrameLayout {

	private AboveView mAboveView;
	private BehindView mBehindView;

    private float mBehindHeight;
    private int mBehindLayoutId;
    private int mAboveLayoutId;

    private SlidingView.OnCloseListener mCloseListener;
    private SlidingView.OnOpenListener mOpenListener;

	public SlidingView(Context context) {
		this(context, null);
	}

	/**
	 * Instantiates a new SlidingMenu.
	 *
	 * @param context the associated Context
	 * @param attrs the attrs
	 */
	public SlidingView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * Instantiates a new SlidingMenu.
	 *
	 * @param context the associated Context
	 * @param attrs the attrs
	 * @param defStyle the def style
	 */
	public SlidingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		LayoutParams behindParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mBehindView = new BehindView(context);
		addView(mBehindView, behindParams);
		LayoutParams aboveParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mAboveView = new AboveView(context);
		addView(mAboveView, aboveParams);
		// register the CustomViewBehind with the CustomViewAbove
		mAboveView.setBehindView(mBehindView);
		mBehindView.setAboveView(mAboveView);

        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.SlidingView, defStyle, 0);

        mBehindHeight = a.getDimension(
                R.styleable.SlidingView_behind_height,
                mBehindHeight);

        mBehindLayoutId = a.getResourceId(R.styleable.SlidingView_behind_layout, R.layout.simple_content);
        mAboveLayoutId  = a.getResourceId(R.styleable.SlidingView_above_layout, R.layout.simple_content);

        setAbove(mAboveLayoutId);
        setBehind(mBehindLayoutId);

        setBehindWidth((int)mBehindHeight);

        a.recycle();
	}
	
	public void setBehindWidth(int h) {
		mBehindView.setBehindHeight(h);
	}
	
	public void setAbove(int res) {
		setAbove(LayoutInflater.from(getContext()).inflate(res, null));
	}

	/**
	 * Set the above view content to the given View.
	 *
	 * @param view The desired content to display.
	 */
	public void setAbove(View view) {
		mAboveView.setContent(view);
		showAbove();
	}

	/**
	 * Retrieves the current content.
	 * @return the current content
	 */
//	public View getAbove() {
//		return mAboveView.getContent();
//	}

	/**
	 * Set the behind view (menu) content from a layout resource. The resource will be inflated, adding all top-level views
	 * to the behind view.
	 *
	 * @param res the new content
	 */
	public void setBehind(int res) {
		setBehind(LayoutInflater.from(getContext()).inflate(res, null));
	}

	/**
	 * Set the behind view (menu) content to the given View.
	 *
	 * @param v The desired content to display.
	 */
	public void setBehind(View v) {
		mBehindView.setContent(v);
	}
	
//	public View getBehind() {
//	return mAboveView.getContent();
//}
	
	/**
	 * Opens the menu and shows the menu view.
	 */
	public void showBehind() {
		showBehind(true);
	}

	/**
	 * Opens the menu and shows the menu view.
	 *
	 * @param animate true to animate the transition, false to ignore animation
	 */
	public void showBehind(boolean animate) {
        if (mOpenListener != null) {
            mOpenListener.onOpen();
        }
		mAboveView.setCurrentItem(0, animate);
	}

	/**
	 * Closes the menu and shows the above view.
	 */
	public void showAbove() {
		showAbove(true);
	}

	/**
	 * Closes the menu and shows the above view.
	 *
	 * @param animate true to animate the transition, false to ignore animation
	 */
	public void showAbove(boolean animate) {
        if (mCloseListener != null) {
            mCloseListener.onClose();
        }
		mAboveView.setCurrentItem(1, animate);
	}

	/**
	 * Toggle the SlidingMenu. If it is open, it will be closed, and vice versa.
	 */
	public void toggle() {
		toggle(true);
	}

	/**
	 * Toggle the SlidingMenu. If it is open, it will be closed, and vice versa.
	 *
	 * @param animate true to animate the transition, false to ignore animation
	 */
	public void toggle(boolean animate) {
		if (isBehindShowing()) {
			showAbove(animate);
		} else {
			showBehind(animate);
		}
	}

	/**
	 * Checks if is the behind view showing.
	 *
	 * @return Whether or not the behind view is showing
	 */
	public boolean isBehindShowing() {
		return mAboveView.getCurrentItem() == 0;
	}

    public interface OnOpenListener {
        public void onOpen();
    }

    public interface OnCloseListener {
        public void onClose();
    }

    public void setOnOpenedListener(SlidingView.OnOpenListener l) {
        mOpenListener = l;
    }

    public void setOnClosedListener(SlidingView.OnCloseListener l) {
        mCloseListener = l;
    }
}
