package cc.dividebyzero.owlninja.base;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;

/**
 *
 *
 */
public abstract class BaseListFragment extends ListFragment implements LoaderManager.LoaderCallbacks{


    protected Context mContext;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity.getApplicationContext();
    }

}
