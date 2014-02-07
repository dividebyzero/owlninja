package cc.dividebyzero.owlninja.history;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import cc.dividebyzero.owlninja.R;

/**
 *
 *
 */
class HistoryCursorAdapter extends CursorAdapter {

    private final LayoutInflater mInflater;
    private int urlColumn;
    private int desColumn;

    public HistoryCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initColumns();
    }


    private void initColumns(){
        Cursor cursor = getCursor();
        urlColumn = cursor.getColumnIndex(HistoryRecord.URL);
        desColumn = cursor.getColumnIndex(HistoryRecord.DESCRIPTION);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.history_item,viewGroup,false);
        //TODO: holder

        final String url = cursor.getString(urlColumn);
        ((TextView)view.findViewById(R.id.tv_history_url)).setText(url);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Cursor swapCursor(Cursor newCursor) {
        Cursor res = super.swapCursor(newCursor);
        initColumns();
        return res;
    }
}
