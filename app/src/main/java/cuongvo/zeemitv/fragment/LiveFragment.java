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
import cuongvo.zeemitv.adapter.LiveAdapter;
import cuongvo.zeemitv.model.LivePOJO;
import cuongvo.zeemitv.util.graphic.InfiniteScrollingListener;
import cuongvo.zeemitv.util.network.request.LiveRequest;
import cuongvo.zeemitv.util.prefs.AppPrefs;

/**
 * Created by cuongmv162 on 7/20/2015.
 */
public class LiveFragment extends BaseFragment {
    private int mPreviousPage = 0;
    private Activity mActivity;
    private ListView mListView;
    private LiveAdapter mLiveAdapter;
    private LiveRequest mLiveRequest;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;

    public LiveFragment(){}

    public static LiveFragment newInstance() {
        LiveFragment fragment = new LiveFragment();
        return fragment;
    }

    public void setActivity(Activity activity){
        this.mActivity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLiveRequest = new LiveRequest(AppPrefs.APP_CREDENTIAL, 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel, null);

        mPreviousPage = mLiveRequest.getPage();

        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.fragment_channel_container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mProgressBar.setVisibility(View.VISIBLE);
                getSpiceManager().execute(mLiveRequest, LiveRequest.CACHE_NAME, DurationInMillis.ONE_MINUTE, refreshRequestListener);
            }
        });

        mProgressBar = (ProgressBar)view.findViewById(R.id.progressbar);
        mListView = (ListView)view.findViewById(R.id.listview);
        mListView.setOnScrollListener(new InfiniteScrollingListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                //Load more data with paging
                if (mLiveRequest != null) {
                    mLiveRequest.setPage(page);
                    mProgressBar.setVisibility(View.VISIBLE);
                    getSpiceManager().execute(mLiveRequest, LiveRequest.CACHE_NAME, DurationInMillis.ALWAYS_EXPIRED, loadMoreRequestListener);
                }
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mProgressBar.setVisibility(View.VISIBLE);
        getSpiceManager().execute(mLiveRequest, LiveRequest.CACHE_NAME, DurationInMillis.ALWAYS_EXPIRED, requestListener);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    RequestListener<LivePOJO.List> requestListener = new RequestListener<LivePOJO.List>() {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            mProgressBar.setVisibility(View.GONE);
        }

        @Override
        public void onRequestSuccess(LivePOJO.List livePOJOs) {

            mProgressBar.setVisibility(View.GONE);

            if(livePOJOs != null){
                if(livePOJOs.size() > 0){
                    mLiveAdapter = new LiveAdapter(mActivity, livePOJOs);
                    mListView.setAdapter(mLiveAdapter);
                    mLiveAdapter.notifyDataSetChanged();
                }
            }
        }
    };

    RequestListener<LivePOJO.List> refreshRequestListener = new RequestListener<LivePOJO.List>() {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            mProgressBar.setVisibility(View.GONE);

        }

        @Override
        public void onRequestSuccess(LivePOJO.List livePOJOs) {
            mProgressBar.setVisibility(View.GONE);

            if (livePOJOs != null) {
                if (livePOJOs.size() > 0) {
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        if(mLiveAdapter != null){
                            mLiveAdapter = null;
                        }
                        mLiveAdapter = new LiveAdapter(mActivity, livePOJOs);
                        mListView.setAdapter(mLiveAdapter);
                        mLiveAdapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }else{
                mSwipeRefreshLayout.setRefreshing(false);

            }
        }
    };


    RequestListener<LivePOJO.List> loadMoreRequestListener = new RequestListener<LivePOJO.List>() {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            mProgressBar.setVisibility(View.GONE);

        }

        @Override
        public void onRequestSuccess(LivePOJO.List livePOJOs) {

            mProgressBar.setVisibility(View.GONE);


            if(livePOJOs != null){
                if (livePOJOs.size() > 0) {
                    if (mLiveAdapter != null) {
                        mLiveAdapter.getmList().addAll(livePOJOs);
                        mLiveAdapter.notifyDataSetChanged();
                    }
                } else {
                    if (mLiveRequest != null) {
                        mLiveRequest.setPage(mPreviousPage);
                    }
                }
            }
        }
    };
}
