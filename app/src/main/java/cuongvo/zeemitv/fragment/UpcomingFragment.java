package cuongvo.zeemitv.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import cuongvo.zeemitv.R;
import cuongvo.zeemitv.adapter.ChannelAdapter;
import cuongvo.zeemitv.model.ChannelPOJO;
import cuongvo.zeemitv.util.graphic.InfiniteScrollingListener;
import cuongvo.zeemitv.util.network.request.ChannelRequest;
import cuongvo.zeemitv.util.prefs.AppPrefs;

/**
 * Created by cuongmv162 on 7/20/2015.
 */
public class UpcomingFragment extends BaseFragment {
    private int mPreviousPage = 0;
    private Activity mActivity;
    private ListView mListView;
    private ChannelAdapter mChannelAdapter;
    private ChannelRequest mChannelRequest;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;

    public UpcomingFragment(){}

    public static UpcomingFragment newInstance() {
        UpcomingFragment fragment = new UpcomingFragment();
        return fragment;
    }

    public void setActivity(Activity activity){
        this.mActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChannelRequest = new ChannelRequest(AppPrefs.APP_CREDENTIAL, 1, ChannelRequest.RequestType.UPCOMING_REQUEST);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel, null);

        mPreviousPage = mChannelRequest.getPage();

        mProgressBar = (ProgressBar)view.findViewById(R.id.progressbar);
        mListView = (ListView)view.findViewById(R.id.listview);
        mListView.setOnScrollListener(new InfiniteScrollingListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (mChannelRequest != null) {
                    mChannelRequest.setPage(page);
                    mProgressBar.setVisibility(View.VISIBLE);
                    getSpiceManager().execute(mChannelRequest, ChannelRequest.UPCOMING_CACHE_NAME, DurationInMillis.ALWAYS_EXPIRED, loadMoreRequestListener);
                }
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.fragment_channel_container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mProgressBar.setVisibility(View.VISIBLE);
                getSpiceManager().execute(mChannelRequest, ChannelRequest.UPCOMING_CACHE_NAME, DurationInMillis.ONE_MINUTE, refreshRequestListener);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        Log.d("CUONGCUONG","onStart");
        super.onStart();
        mProgressBar.setVisibility(View.VISIBLE);
        getSpiceManager().execute(mChannelRequest, ChannelRequest.UPCOMING_CACHE_NAME, DurationInMillis.ALWAYS_EXPIRED, requestListener);

    }

    RequestListener<ChannelPOJO.List> requestListener = new RequestListener<ChannelPOJO.List>() {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            mProgressBar.setVisibility(View.GONE);

        }

        @Override
        public void onRequestSuccess(ChannelPOJO.List channelPOJOs) {
            mProgressBar.setVisibility(View.GONE);

            if(channelPOJOs != null){
                if(channelPOJOs.size() > 0){
                    mChannelAdapter = new ChannelAdapter(mActivity, channelPOJOs);
                    mListView.setAdapter(mChannelAdapter);
                    mChannelAdapter.notifyDataSetChanged();
                }
            }
        }
    };

    RequestListener<ChannelPOJO.List> refreshRequestListener = new RequestListener<ChannelPOJO.List>() {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            mProgressBar.setVisibility(View.GONE);

        }

        @Override
        public void onRequestSuccess(ChannelPOJO.List channelPOJOs) {
            mProgressBar.setVisibility(View.GONE);

            if (channelPOJOs != null) {
                if(channelPOJOs.size() > 0) {
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        if(mChannelAdapter != null){
                            mChannelAdapter = null;
                        }
                        mChannelAdapter = new ChannelAdapter(mActivity, channelPOJOs);
                        mListView.setAdapter(mChannelAdapter);
                        mChannelAdapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }else{
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }else{
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }
    };


    RequestListener<ChannelPOJO.List> loadMoreRequestListener = new RequestListener<ChannelPOJO.List>() {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            mProgressBar.setVisibility(View.GONE);

        }

        @Override
        public void onRequestSuccess(ChannelPOJO.List channelPOJOs) {
            mProgressBar.setVisibility(View.GONE);

            if(channelPOJOs != null){
                if (channelPOJOs.size() > 0) {
                    if (mChannelAdapter != null) {
                        mChannelAdapter.getmList().addAll(channelPOJOs);
                        mChannelAdapter.notifyDataSetChanged();
                    }
                } else {
                    if (mChannelRequest != null) {
                        mChannelRequest.setPage(mPreviousPage);
                    }
                }
            }
        }
    };
}
