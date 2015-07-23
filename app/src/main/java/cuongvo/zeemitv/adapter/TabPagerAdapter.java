package cuongvo.zeemitv.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cuongvo.zeemitv.fragment.LiveFragment;
import cuongvo.zeemitv.fragment.PopularFragment;
import cuongvo.zeemitv.fragment.UpcomingFragment;

/**
 * Created by cuongmv162 on 7/20/2015.
 */
public class TabPagerAdapter extends FragmentPagerAdapter {
    private static final int NUMBERS_OFF_PAGE  = 3;
    private String tabTitles[] = new String[] { "LIVE", "UPCOMING", "POPULAR" };

    private Context mContext;

    private LiveFragment mLiveFragment;
    private PopularFragment mPopularFragment;
    private UpcomingFragment mUpcomingFragment;

    public TabPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;

        mLiveFragment = LiveFragment.newInstance();
        mLiveFragment.setActivity((Activity)mContext);

        mPopularFragment = PopularFragment.newInstance();
        mPopularFragment.setActivity((Activity)mContext);

        mUpcomingFragment = UpcomingFragment.newInstance();
        mUpcomingFragment.setActivity((Activity)mContext);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return mLiveFragment;
            case 1:
                return mUpcomingFragment;
            case 2:
                return mPopularFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUMBERS_OFF_PAGE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }


}
