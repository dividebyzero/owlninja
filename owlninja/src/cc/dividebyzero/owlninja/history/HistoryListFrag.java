package cc.dividebyzero.owlninja.history;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.CursorAdapter;
import cc.dividebyzero.owlninja.base.BaseListFragment;

/**
 *
 *
 */
public class HistoryListFrag extends BaseListFragment {

    private static final int LOADER_ID_HISTORY = 1959; //year of the first moon landing

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(LOADER_ID_HISTORY,null,this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle bundle) {
        if (LOADER_ID_HISTORY == id) {
            CursorLoader cl = new CursorLoader(
                    mContext,
                    Uri.parse(HistoryRecord.getContentURI()),
                    HistoryRecord.getDefaultProjectionMap(),
                    null,
                    null,
                    HistoryRecord.TIMESTAMP
            );
            return cl;
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object o) {
        if(LOADER_ID_HISTORY ==loader.getId()){
            Cursor c = (Cursor)o;
            CursorAdapter ca = ((CursorAdapter)getListAdapter());
            if(ca==null){
                ca = new HistoryCursorAdapter(mContext,c,true);
                setListAdapter(ca);
            }
            ca.swapCursor(c);
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
