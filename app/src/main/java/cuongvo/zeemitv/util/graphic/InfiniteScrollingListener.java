package cuongvo.zeemitv.util.graphic;

import android.util.Log;
import android.widget.AbsListView;

/**
 * Created by cuongmv162 on 7/22/2015.
 */
public abstract class InfiniteScrollingListener implements AbsListView.OnScrollListener {
    private int visibleThreshold = 5;
    private int currentPage = 0;
    private int previousTotalItemCount = 0;
    private boolean loading = true;
    private int startingPageIndex = 0;

    public InfiniteScrollingListener() {
    }

    public InfiniteScrollingListener(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    public InfiniteScrollingListener(int visibleThreshold, int startPage) {
        this.visibleThreshold = visibleThreshold;
        this.startingPageIndex = startPage;
        this.currentPage = startPage;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.d("Cuong", "onScroll ==> firstVisibleItem " + firstVisibleItem);
        Log.d("Cuong", "onScroll ==> visibleItemCount " + visibleItemCount);
        Log.d("Cuong", "onScroll ==> totalItemCount " + totalItemCount);
        Log.d("Cuong", "onScroll ==> currentPage " + currentPage);


        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;

            if (totalItemCount == 0) {
                this.loading = true;
            }
        }

        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
            currentPage++;
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            onLoadMore(currentPage + 1, totalItemCount);
            loading = true;
        }


    }

    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int page, int totalItemsCount);

}

