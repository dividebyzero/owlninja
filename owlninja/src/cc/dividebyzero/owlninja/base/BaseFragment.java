package cc.dividebyzero.owlninja.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

/**
 *
 *
 */
public class BaseFragment extends Fragment{


    protected Context mContext;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity.getApplicationContext();
    }
}
