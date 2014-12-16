package com.moorle.bookkeeping.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moorle.bookkeeping.R;
import com.moorle.bookkeeping.ui.widget.SlidingTabLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeFragment extends BaseFragment {

    public IncomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  super.onCreateView(inflater, container, savedInstanceState);

        return rootView;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_income;
    }


}
