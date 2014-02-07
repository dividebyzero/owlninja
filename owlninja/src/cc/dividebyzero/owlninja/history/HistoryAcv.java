package cc.dividebyzero.owlninja.history;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import cc.dividebyzero.owlninja.R;
import cc.dividebyzero.owlninja.base.BaseActivity;

/**
 *
 *
 */
public class HistoryAcv extends BaseActivity {

    HistoryListFrag hiss = new HistoryListFrag(); //easy as she goes.. no args nothing.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_acv);

        FragmentTransaction fragTrac = getFragmentManager().beginTransaction();

        fragTrac.add(R.id.list_container,hiss);
        fragTrac.commit();

        initActionBar();
    }

    private void initActionBar() {
        //FIXME
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


}
