package cuongvo.zeemitv.fragment;

import android.app.Activity;
import android.os.Bundle;
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
public class PopularFragment extends BaseFragment {
    private boolean isHaveNewData = false;
    private Activity mActivity;
    private ListView mListView;
    private ChannelRequest mPopularRequest;
    private ChannelAdapter mChannelAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mPreviousPage = 0;
    private ProgressBar mProgressBar;

    public PopularFragment(){}

    public static PopularFragment newInstance() {
        PopularFragment fragment = new PopularFragment();
        return fragment;
    }

    public void setActivity(Activity activity){
        this.mActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPopularRequest = new ChannelRequest(AppPrefs.APP_CREDENTIAL, 1, ChannelRequest.RequestType.POPULAR_REQUEST);
        Log.d("Cuong", "PopularFragment onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mPreviousPage = mPopularRequest.getPage();

        View view = inflater.inflate(R.layout.fragment_channel, null);


        mProgressBar = (ProgressBar)view.findViewById(R.id.progressbar);

        mListView = (ListView)view.findViewById(R.id.listview);
        mListView.setOnScrollListener(new InfiniteScrollingListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                //Load more data with paging
                if(mPopularRequest != null){
                    mPopularRequest.setPage(page);
                    mProgressBar.setVisibility(View.VISIBLE);
                    getSpiceManager().execute(mPopularRequest, ChannelRequest.POPULAR_CACHE_NAME, DurationInMillis.ALWAYS_EXPIRED, loadMoreRequestListener);
                }
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.fragment_channel_container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mProgressBar.setVisibility(View.VISIBLE);
                getSpiceManager().execute(mPopularRequest, ChannelRequest.POPULAR_CACHE_NAME, DurationInMillis.ONE_MINUTE, refreshRequestListener);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mProgressBar.setVisibility(View.VISIBLE);
        getSpiceManager().execute(mPopularRequest, ChannelRequest.POPULAR_CACHE_NAME, DurationInMillis.ALWAYS_EXPIRED, requestListener);
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
                    if (mPopularRequest != null) {
                        mPopularRequest.setPage(mPreviousPage);
                    }
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

}
