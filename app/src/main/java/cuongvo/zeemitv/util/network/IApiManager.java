package cuongvo.zeemitv.util.network;

import cuongvo.zeemitv.model.ChannelPOJO;
import cuongvo.zeemitv.model.LivePOJO;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by cuongmv162 on 7/21/2015.
 */
public interface IApiManager {
    static final String ACCESS_TOKEN = "access_token";
    static final String PAGE = "page";

    @GET("/live.json")
    LivePOJO.List getLiveData(@Query(ACCESS_TOKEN) String accessToken);

    @GET("/live.json")
    LivePOJO.List getLiveDataWithPage(@Query(ACCESS_TOKEN) String accessToken, @Query(PAGE) String page);

    @GET("/upcoming.json")
    ChannelPOJO.List getUpcomingData(@Query(ACCESS_TOKEN) String accessToken);

    @GET("/upcoming.json")
    ChannelPOJO.List getUpcomingDataWithPage(@Query(ACCESS_TOKEN) String accessToken, @Query(PAGE) String page);


    @GET("/popular.json")
    ChannelPOJO.List getPopularData(@Query(ACCESS_TOKEN) String accessToken);

    @GET("/popular.json")
    ChannelPOJO.List getPopularDataWithPage(@Query(ACCESS_TOKEN) String accessToken, @Query(PAGE) String page);
}
