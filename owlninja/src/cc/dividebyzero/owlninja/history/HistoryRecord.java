package cc.dividebyzero.owlninja.history;


import cc.dividebyzero.owlninja.base.BaseColumns;
import cc.dividebyzero.owlninja.provider.HistoryProvider;

/**
 *
 *
 */
public class HistoryRecord extends BaseColumns {


    public static final String TIMESTAMP = "timestamp";
    public static final String URL ="url";
    public static final String DESCRIPTION ="description";
    public static final String RATING ="rating";
    public static final String LAST_ACTIVITY = "last_activity";
    public static final String DELETED ="deleted";

    private static final String[] DEFAULT_PROJECTION_MAP =
            {
                  _ID,
                  TIMESTAMP,
                  URL,
                  DESCRIPTION,
                  RATING,
                  LAST_ACTIVITY,
                  DELETED
            };



    public static String getContentURI() {
        return "content://"+ HistoryProvider.AUTHORITY+"/history";
    }

    public static String[] getDefaultProjectionMap(){
        return DEFAULT_PROJECTION_MAP;
    }


}
