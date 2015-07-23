package cuongvo.zeemitv.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import cuongvo.zeemitv.util.network.ApiService;

/**
 * Created by cuongmv162 on 7/22/2015.
 */
public class BaseFragment extends Fragment {
    private SpiceManager spiceManager = new SpiceManager(ApiService.class);

    private Activity mActivity;

    public BaseFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = getActivity();

    }

    @Override
    public void onStart() {
        spiceManager.start(mActivity);
        super.onStart();
    }

    @Override
    public void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    protected SpiceManager getSpiceManager() {
        return spiceManager;
    }

    public final class ListResponseListener implements RequestListener<Object> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(mActivity, "failure", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestSuccess(Object t) {

        }
    }
}
