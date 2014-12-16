package com.moorle.bookkeeping.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.melnykov.fab.FloatingActionButton;
import com.moorle.bookkeeping.R;
import com.moorle.bookkeeping.ui.widget.SlidingTabLayout;
import com.moorle.bookkeeping.ui.widget.SlidingView;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, SlidingView.OnOpenListener, SlidingView.OnCloseListener {
    private static final String TAG = "ExpenseFragment";

    public static final String DATEPICKER_TAG = "datepicker";
    public static final String TIMEPICKER_TAG = "timepicker";

    private DatePickerDialog mDatePickerDialog;
    private TimePickerDialog mTimePickerDialog;

    @InjectView(R.id.date) Button mDateView;
    @InjectView(R.id.time) Button mTimeView;
    @InjectView(R.id.sliding_view) SlidingView mSlidingView;

    @InjectView(R.id.fab) FloatingActionButton mFab;
    @InjectView(R.id.expense_list) ListView mExpenseList;


    RecordFragment.SlidingStateCallbacks mSlidingCallback;

    public ExpenseFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Calendar calendar = Calendar.getInstance();

        mDatePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), true);
        mTimePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false, false);


        setOnSlidingCallback((RecordFragment.SlidingStateCallbacks) getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        mSlidingView.setOnOpenedListener(this);
        mSlidingView.setOnClosedListener(this);
        mFab.attachToListView(mExpenseList);
        return rootView;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_expense;
    }

    @OnClick(R.id.date)
    public void onClickDateView(Button button) {
        mDatePickerDialog.setVibrate(true);
        mDatePickerDialog.setYearRange(1985, 2028);
        mDatePickerDialog.setCloseOnSingleTapDay(false);
        mDatePickerDialog.show(getChildFragmentManager(), DATEPICKER_TAG);
    }

    @OnClick(R.id.time)
    public void onClickTimeView(Button button) {
        mTimePickerDialog.setVibrate(true);
        mTimePickerDialog.setCloseOnSingleTapMinute(false);
        mTimePickerDialog.show(getChildFragmentManager(), TIMEPICKER_TAG);
    }

    @OnClick(R.id.fab)
    public void onClickFab(View v) {
        mSlidingView.toggle();
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int i, int i2, int i3) {

    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i2) {

    }

    public void setOnSlidingCallback(RecordFragment.SlidingStateCallbacks callback) {
        this.mSlidingCallback = callback;
    }

    @Override
    public void onOpen() {
        if (mSlidingCallback != null) {
            mSlidingCallback.onSlidingViewOpened(this);
        }
    }

    @Override
    public void onClose() {
        if (mSlidingCallback != null) {
            mSlidingCallback.onSlidingViewClosed(this);
        }
    }

    public boolean isBehindShowing() {
        return mSlidingView.isBehindShowing();
    }
}
