package com.moorle.bookkeeping.ui;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moorle.bookkeeping.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionFragment extends BaseFragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String STATE_SELECTED_ITEM_ID = "selected_nav_drawer_item_id";

    public TransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(STATE_SELECTED_ITEM_ID));
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TransactionFragment newInstance(int sectionNumber) {
        TransactionFragment fragment = new TransactionFragment();
        Bundle args = new Bundle();
        args.putInt(STATE_SELECTED_ITEM_ID, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_transaction;
    }


}
