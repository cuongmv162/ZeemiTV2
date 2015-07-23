package cuongvo.zeemitv.util.network.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import cuongvo.zeemitv.model.LivePOJO;
import cuongvo.zeemitv.util.network.IApiManager;

/**
 * Created by cuongmv162 on 7/21/2015.
 */
public class LiveRequest extends RetrofitSpiceRequest<LivePOJO.List, IApiManager> {
    public static final String CACHE_NAME = "liveRequestCache";
    private String accessToken;
    private int page;

    public LiveRequest(String accessToken) {
        super(LivePOJO.List.class, IApiManager.class);
        this.accessToken = accessToken;
    }

    public LiveRequest(String accessToken, int page){
        super(LivePOJO.List.class, IApiManager.class);
        this.accessToken = accessToken;
        this.page = page;
    }

    @Override
    public LivePOJO.List loadDataFromNetwork() throws Exception {
        return getService().getLiveDataWithPage(accessToken, String.valueOf(page));
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage(){
        return this.page;
    }
}
