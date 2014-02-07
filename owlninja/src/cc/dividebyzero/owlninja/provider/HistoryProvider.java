package cc.dividebyzero.owlninja.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import cc.dividebyzero.owlninja.archive.ArchiveRecord;
import cc.dividebyzero.owlninja.history.HistoryRecord;

/**
 *
 *
 */
public class HistoryProvider extends ContentProvider {

    public static final String AUTHORITY = "owlninja";

    private static final int VERSION = 1;
    private static final String DBNAME ="the_great_ninja_war.db";
    private DBHelper dbHelper;

    private static final String TABLE_HISTORY = "history";
    private static final String TABLE_ARCHIVE = "archive";
    private static class DBHelper extends SQLiteOpenHelper{



        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            StringBuffer s = new StringBuffer();

            s.append("CREATE TABLE ");
            s.append(TABLE_HISTORY);
            s.append("(");
            s.append(HistoryRecord._ID);
            s.append(" INTEGER PRIMARY KEY,");
            s.append(HistoryRecord.TIMESTAMP);
            s.append(" INTEGER,");
            s.append(HistoryRecord.URL);
            s.append(" STRING,");
            s.append(HistoryRecord.DESCRIPTION);
            s.append(" STRING,");
            s.append(HistoryRecord.LAST_ACTIVITY);
            s.append(" INTEGER,");
            s.append(HistoryRecord.RATING);
            s.append(" INTEGER,");
            s.append(HistoryRecord.DELETED);
            s.append(" INTEGER");
            s.append(");");
            sqLiteDatabase.execSQL(s.toString());
            s.setLength(0);

            s.append("CREATE TABLE ");
            s.append(TABLE_ARCHIVE);
            s.append("(");
            s.append(ArchiveRecord._ID);
            s.append(" INTEGER PRIMARY KEY,");
            s.append(ArchiveRecord.TIMESTAMP);
            s.append(" INTEGER,");
            s.append(ArchiveRecord.ARCHIVED_TIMESTAMP);
            s.append(" INTEGER,");            
            s.append(ArchiveRecord.URL);
            s.append(" STRING,");
            s.append(ArchiveRecord.DESCRIPTION);
            s.append(" STRING,");
            s.append(ArchiveRecord.LAST_ACTIVITY);
            s.append(" INTEGER,");
            s.append(ArchiveRecord.RATING);
            s.append(" INTEGER,");
            s.append(ArchiveRecord.DELETED);
            s.append(" INTEGER");
            s.append(");");
            sqLiteDatabase.execSQL(s.toString());

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
            //empty for now
        }
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext(),DBNAME,null,VERSION);

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] columns, String selection, String[] selArgs, String s2) {
        final SQLiteDatabase database = dbHelper.getReadableDatabase();


        Cursor c =null;
        switch (uriMatcher.match(uri)) {

            case URI_HISTORY:
                c = database.query(TABLE_HISTORY,
                        columns,
                        selection,
                        selArgs,
                        null,
                        null,
                        null
                );
                break;

            case URI_HISTORY_ID:
                c = database.query(TABLE_HISTORY,
                        columns,
                        "_ID="+uri.getLastPathSegment()+" AND "+selection,
                        selArgs,
                        null,
                        null,
                        null
                );
                break;

            case URI_ARCHIVE:
                c = database.query(TABLE_ARCHIVE,
                        columns,
                        selection,
                        selArgs,
                        null,
                        null,
                        null
                );
                break;

            case URI_ARCHIVE_ID:
                c = database.query(TABLE_ARCHIVE,
                        columns,
                        "_ID=" + uri.getLastPathSegment() + " AND " + selection,
                        selArgs,
                        null,
                        null,
                        null
                );
                break;

            default:
                throw new IllegalArgumentException("unknown uri >>"+uri+"<<");
        }



        return c;
    }

    @Override
    public String getType(Uri uri) {
        //FIXME
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        final long now = System.currentTimeMillis();

        if(!values.containsKey(HistoryRecord.TIMESTAMP)){
            values.put(HistoryRecord.TIMESTAMP, now);
        }

        if(!values.containsKey(HistoryRecord.LAST_ACTIVITY)){
            values.put(HistoryRecord.LAST_ACTIVITY,now);
        }

        switch (uriMatcher.match(uri)){
            case URI_HISTORY:
                database.insertOrThrow(TABLE_HISTORY,null,values);
                break;
            case URI_ARCHIVE:

                if(!values.containsKey(ArchiveRecord.ARCHIVED_TIMESTAMP)){
                    values.put(ArchiveRecord.ARCHIVED_TIMESTAMP,now);
                }

                database.insertOrThrow(TABLE_ARCHIVE, null, values);
                break;
            default:
                throw new IllegalArgumentException("unknown uri >>" + uri + "<<");
        }

        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selArgs) {
        int deadRecords = 0;
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case URI_HISTORY:
                deadRecords = database.delete(TABLE_HISTORY, selection, selArgs);
                break;
            case URI_HISTORY_ID:
                deadRecords = database.delete(TABLE_HISTORY,
                        "_ID="+uri.getLastPathSegment()+" AND "+selection,
                        selArgs
                );
                break;
            case URI_ARCHIVE:
                deadRecords = database.delete(TABLE_HISTORY, selection, selArgs);
                break;
            case URI_ARCHIVE_ID:
                deadRecords = database.delete(TABLE_HISTORY,
                        "_ID=" + uri.getLastPathSegment() + " AND " + selection,
                        selArgs
                );
                break;
            default:
                throw new IllegalArgumentException("unknown uri >>" + uri + "<<");
        }

        return deadRecords;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selArgs) {
        int updatedRecords = 0;
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        final long now = System.currentTimeMillis();

        if (!values.containsKey(HistoryRecord.LAST_ACTIVITY)) {
            values.put(HistoryRecord.LAST_ACTIVITY, now);
        }

        switch (uriMatcher.match(uri)) {
            case URI_HISTORY:
                updatedRecords = database.update(TABLE_HISTORY,values, selection, selArgs);
                break;
            case URI_HISTORY_ID:
                updatedRecords = database.update(TABLE_HISTORY,
                        values,
                        "_ID=" + uri.getLastPathSegment() + " AND " + selection,
                        selArgs
                );
                break;
            case URI_ARCHIVE:
                updatedRecords = database.update(TABLE_HISTORY,values, selection, selArgs);
                break;
            case URI_ARCHIVE_ID:
                updatedRecords = database.update(TABLE_HISTORY,
                        values,
                        "_ID=" + uri.getLastPathSegment() + " AND " + selection,
                        selArgs
                );
                break;
            default:
                throw new IllegalArgumentException("unknown uri >>" + uri + "<<");
        }
        return updatedRecords;
    }


    private static final int URI_HISTORY = 1;

    private static final int URI_ARCHIVE = 2;

    private static final int URI_HISTORY_ID = 11;

    private static final int URI_ARCHIVE_ID = 12;

    static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("owlninja","history",URI_HISTORY);
        uriMatcher.addURI("owlninja","archive",URI_ARCHIVE);

        uriMatcher.addURI("owlninja", "history/#", URI_HISTORY_ID);
        uriMatcher.addURI("owlninja", "archive/#", URI_ARCHIVE_ID);

    }
}
