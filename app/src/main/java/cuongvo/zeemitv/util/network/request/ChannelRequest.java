package cuongvo.zeemitv.util.network.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import cuongvo.zeemitv.model.ChannelPOJO;
import cuongvo.zeemitv.util.network.IApiManager;

/**
 * Created by cuongmv162 on 7/21/2015.
 */
public class ChannelRequest extends RetrofitSpiceRequest<ChannelPOJO.List, IApiManager> {

    public enum RequestType{
        UPCOMING_REQUEST(1, "upcoming_request"),
        POPULAR_REQUEST(2, "popular_request");

        int id;
        String request;

        RequestType(int id, String name){
            this.id = id;
            this.request = name;
        }

        public int getId(){
            return this.id;
        }

        public String getRequest(){
            return this.request;
        }

    }

    public static final String POPULAR_CACHE_NAME = "popularRequestCache";
    public static final String UPCOMING_CACHE_NAME = "upcomingRequestCache";
    private String mAccessToken;
    private int mPage;
    private RequestType mRequestType;

    public ChannelRequest(String accessToken, ChannelRequest.RequestType requestType) {
        super(ChannelPOJO.List.class, IApiManager.class);
        this.mAccessToken = accessToken;
        this.mRequestType = requestType;
    }

    public ChannelRequest(String accessToken, int page, ChannelRequest.RequestType requestType){
        super(ChannelPOJO.List.class, IApiManager.class);
        this.mAccessToken = accessToken;
        this.mPage = page;
        this.mRequestType = requestType;
    }

    @Override
    public ChannelPOJO.List loadDataFromNetwork() throws Exception {
        switch (mRequestType){
            case POPULAR_REQUEST:
                return getService().getPopularDataWithPage(mAccessToken, String.valueOf(mPage));
            case UPCOMING_REQUEST:
                return getService().getUpcomingDataWithPage(mAccessToken, String.valueOf(mPage));
            default:
                return null;
        }
    }


    public void setPage(int mPage) {
        this.mPage = mPage;
    }

    public int getPage(){
        return this.mPage;
    }
}
