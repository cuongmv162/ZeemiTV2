package cuongvo.zeemitv;

import android.support.v4.app.FragmentActivity;

import com.octo.android.robospice.SpiceManager;

import cuongvo.zeemitv.util.network.ApiService;

/**
 * Created by cuongmv162 on 7/22/2015.
 */
public class BaseActivity extends FragmentActivity {
    private SpiceManager spiceManager = new SpiceManager(ApiService.class);

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
