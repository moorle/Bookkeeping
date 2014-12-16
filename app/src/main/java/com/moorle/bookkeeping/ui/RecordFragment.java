package com.moorle.bookkeeping.ui;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moorle.bookkeeping.R;
import com.moorle.bookkeeping.ui.widget.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;


public class RecordFragment extends BaseFragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String STATE_SELECTED_ITEM_ID = "selected_nav_drawer_item_id";

    @InjectView(R.id.sliding_tabs) SlidingTabLayout mSlidingTabLayout;
    @InjectView(R.id.view_pager) ViewPager mViewPager;

    private FragmentPagerAdapter mViewPagerAdapter;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RecordFragment newInstance(int sectionNumber) {
        RecordFragment fragment = new RecordFragment();
        Bundle args = new Bundle();
        args.putInt(STATE_SELECTED_ITEM_ID, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public RecordFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        mViewPagerAdapter = new InExViewPagerAdapter(getChildFragmentManager(), createFragments(), createPageTitles());
        mViewPager.setAdapter(mViewPagerAdapter);

        mSlidingTabLayout.setCustomTabView(R.layout.tab_indicator, android.R.id.text1);

        //setSlidingTabLayoutContentDescriptions();

        Resources res = getResources();
        mSlidingTabLayout.setSelectedIndicatorColors(res.getColor(R.color.tab_selected_strip));
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);

        return rootView;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_record;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(STATE_SELECTED_ITEM_ID));
    }

    private List<Fragment> createFragments() {
        List<Fragment> frags = new ArrayList<Fragment>();
        frags.add(new ExpenseFragment());
        frags.add(new IncomeFragment());
        return frags;
    }

    private List<String> createPageTitles() {
        List<String> pageTitles = new ArrayList<String>();
        pageTitles.add(getString(R.string.expense));
        pageTitles.add(getString(R.string.income));

        return pageTitles;
    }

    private class InExViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mItems;
        private List<String> mPageTitles;
        public InExViewPagerAdapter(FragmentManager fm, List<Fragment> items, List<String> pageTitles) {
            super(fm);
            mItems = items;
            mPageTitles = pageTitles;
        }

        @Override
        public Fragment getItem(int position) {
            return mItems.get(position);
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mPageTitles.get(position);
        }

    }

    public interface SlidingStateCallbacks {
        void onSlidingViewClosed(Fragment f);
        void onSlidingViewOpened(Fragment f);
    }
}