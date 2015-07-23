package cuongvo.zeemitv.util.network;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

/**
 * Created by cuongmv162 on 7/21/2015.
 */
public class ApiService extends RetrofitGsonSpiceService {
    private static final String BASE_URL = "http://api-staging.zeemi.tv/1/channels";

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(IApiManager.class);
    }

    @Override
    protected String getServerUrl() {
        return BASE_URL;
    }
}
