package cc.dividebyzero.owlninja.test.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;
import cc.dividebyzero.owlninja.history.HistoryRecord;
import cc.dividebyzero.owlninja.provider.HistoryProvider;

/**
 *
 *
 */
public class TestHistoryProvider extends ProviderTestCase2 {


    public static final String URL ="https://github.com/dividebyzero/owlninja";
    public static final String DESCR = "owls and ninjas";
    public static final int RATING = 4;

    public static final String UPDATED_URL = "http://media.giphy.com/media/SWFdrNcQPIyYw/giphy.gif";
    public static final String UPDATED_DESCR = "say hello to my little friend";
    public static final int UPDATED_RATING = 5;

    public TestHistoryProvider() {
        super(HistoryProvider.class, HistoryProvider.AUTHORITY);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testHistoryActions(){
        final ContentResolver contentResolver = getMockContentResolver();

        ContentValues values = new ContentValues();
        values.put(HistoryRecord.DESCRIPTION,DESCR);
        values.put(HistoryRecord.URL,URL);
        values.put(HistoryRecord.RATING,RATING);

        Uri historyUri = Uri.parse(HistoryRecord.getContentURI());

        Uri insertedUri = contentResolver.insert(historyUri, values);
        assertNotNull(insertedUri);

        Cursor c = contentResolver.query(
                historyUri,
                HistoryRecord.getDefaultProjectionMap(),
                null,
                null,
                null
        );

        assertNotNull(c);
        assertTrue(c.getCount()>0);
        c.close();

        c = contentResolver.query(
                insertedUri,
                HistoryRecord.getDefaultProjectionMap(),
                null,
                null,
                null
        );
        assertNotNull(c);
        assertTrue(c.getCount() > 0);
        c.moveToFirst();
        String url = c.getString(c.getColumnIndex(HistoryRecord.URL));
        String desc = c.getString(c.getColumnIndex(HistoryRecord.DESCRIPTION));
        int deleted = c.getInt(c.getColumnIndex(HistoryRecord.DELETED));
        long timestamp = c.getLong(c.getColumnIndex(HistoryRecord.TIMESTAMP));
        long lastActivity = c.getLong(c.getColumnIndex(HistoryRecord.LAST_ACTIVITY));
        int rating = c.getInt(c.getColumnIndex(HistoryRecord.RATING));

        assertEquals(URL,url);
        assertEquals(DESCR,desc);
        assertTrue(RATING==rating);
        assertTrue(timestamp > 0);
        assertTrue(timestamp == lastActivity);

        c.close();

        ContentValues updatedValues = new ContentValues();
        updatedValues.put(HistoryRecord.DESCRIPTION,UPDATED_DESCR);
        updatedValues.put(HistoryRecord.URL,UPDATED_URL);
        updatedValues.put(HistoryRecord.RATING,UPDATED_RATING);

        final int updatedRows=contentResolver.update(insertedUri,updatedValues,null,null);
        assertTrue(updatedRows == 1);

        c = contentResolver.query(
                insertedUri,
                HistoryRecord.getDefaultProjectionMap(),
                null,
                null,
                null
        );
        assertNotNull(c);
        assertTrue(c.getCount() > 0);
        c.moveToFirst();
        url = c.getString(c.getColumnIndex(HistoryRecord.URL));
        desc = c.getString(c.getColumnIndex(HistoryRecord.DESCRIPTION));
        deleted = c.getInt(c.getColumnIndex(HistoryRecord.DELETED));
        timestamp = c.getLong(c.getColumnIndex(HistoryRecord.TIMESTAMP));
        lastActivity = c.getLong(c.getColumnIndex(HistoryRecord.LAST_ACTIVITY));
        rating = c.getInt(c.getColumnIndex(HistoryRecord.RATING));

        assertEquals(UPDATED_URL, url);
        assertEquals(UPDATED_DESCR, desc);
        assertTrue(UPDATED_RATING == rating);
        assertTrue(timestamp > 0);
        assertTrue(lastActivity >0);
        assertTrue(timestamp != lastActivity);

        c.close();


        final int deletedRows = contentResolver.delete(insertedUri,null,null);
        assertTrue(deletedRows == 1);

    }
}
