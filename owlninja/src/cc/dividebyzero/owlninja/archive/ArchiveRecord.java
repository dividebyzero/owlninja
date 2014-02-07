package cc.dividebyzero.owlninja.archive;

import cc.dividebyzero.owlninja.history.HistoryRecord;
import cc.dividebyzero.owlninja.provider.HistoryProvider;

/**
 *
 *
 */
public class ArchiveRecord extends HistoryRecord {

    public static final String ARCHIVED_TIMESTAMP ="archived_timestamp";

    private static final String[] DEFAULT_PROJECTION_MAP =
            {
                    _ID,
                    TIMESTAMP,
                    ARCHIVED_TIMESTAMP,
                    URL,
                    DESCRIPTION,
                    RATING,
                    LAST_ACTIVITY,
                    DELETED
            };



    public static String getContentURI() {
        return "content://" + HistoryProvider.AUTHORITY + "/archive";
    }

    public static String[] getDefaultProjectionMap() {
        return DEFAULT_PROJECTION_MAP;
    }

}
