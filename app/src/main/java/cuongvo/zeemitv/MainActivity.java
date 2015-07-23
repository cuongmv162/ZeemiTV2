package cuongvo.zeemitv;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;

import cuongvo.zeemitv.adapter.TabPagerAdapter;
import cuongvo.zeemitv.util.network.request.LiveRequest;
import cuongvo.zeemitv.util.prefs.AppPrefs;


public class MainActivity extends BaseActivity {

    private LiveRequest liveRequest;
    private ViewPager mViewPager;
    private TabPagerAdapter mMainPagerAdapter;
    private com.astuetz.PagerSlidingTabStrip mPagerSlidingTabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        liveRequest = new LiveRequest(AppPrefs.APP_CREDENTIAL);

        mViewPager = (ViewPager)findViewById(R.id.activity_viewpager);
        mMainPagerAdapter = new TabPagerAdapter(MainActivity.this, getSupportFragmentManager());
        mViewPager.setAdapter(mMainPagerAdapter);
        mViewPager.setCurrentItem(0);

        mPagerSlidingTabStrip = (PagerSlidingTabStrip)findViewById(R.id.tabs);
        mPagerSlidingTabStrip.setViewPager(mViewPager);

    }
}
