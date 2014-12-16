package com.moorle.bookkeeping.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.moorle.bookkeeping.R;

import butterknife.InjectView;


public class MainActivity extends BaseActivity
        implements NavDrawerFragment.NavDrawerCallbacks, RecordFragment.SlidingStateCallbacks{

    private static final String TAG = "MainActivity";
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavDrawerFragment mNavDrawerFragment;

    private Fragment mCurrentFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNavDrawerFragment = (NavDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavDrawerFragment.setupNavDrawer(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void onNavDrawerItemSelected(int itemId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (itemId) {
            case NavDrawerFragment.NAVDRAWER_ITEM_BOOKKEEPING:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, RecordFragment.newInstance(itemId))
                        .commit();
                break;
            case NavDrawerFragment.NAVDRAWER_ITEM_TRANSACTION:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, TransactionFragment.newInstance(itemId))
                        .commit();
                break;
            case NavDrawerFragment.NAVDRAWER_ITEM_REPORT:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ReportFragment.newInstance(itemId))
                        .commit();
                break;
            case NavDrawerFragment.NAVDRAWER_ITEM_ACCOUNT:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, AccountFragment.newInstance(itemId))
                        .commit();
                break;

        }

        mCurrentFragment = null;
    }

    public void onSectionAttached(int itemId) {
        switch (itemId) {
            case NavDrawerFragment.NAVDRAWER_ITEM_BOOKKEEPING:
                mTitle = getString(R.string.navdrawer_item_book_keeping);
                break;
            case NavDrawerFragment.NAVDRAWER_ITEM_TRANSACTION:
                mTitle = getString(R.string.navdrawer_item_transactions);
                break;
            case NavDrawerFragment.NAVDRAWER_ITEM_REPORT:
                mTitle = getString(R.string.navdrawer_item_reports);
                break;
            case NavDrawerFragment.NAVDRAWER_ITEM_ACCOUNT:
                mTitle = getString(R.string.navdrawer_item_account);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            if(mCurrentFragment instanceof ExpenseFragment && ((ExpenseFragment) mCurrentFragment).isBehindShowing()) {
                getMenuInflater().inflate(R.menu.menu_main, menu);
            }

            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSlidingViewClosed(Fragment f) {
        supportInvalidateOptionsMenu();
        mCurrentFragment = f;
    }

    @Override
    public void onSlidingViewOpened(Fragment f) {
        supportInvalidateOptionsMenu();
        mCurrentFragment = f;
    }
}
