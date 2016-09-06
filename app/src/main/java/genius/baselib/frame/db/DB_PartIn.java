package genius.baselib.frame.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.miniram.donpush.cccid.base.BaseDB;
import com.miniram.donpush.cccid.bean.PartInBean;

import genius.utils.UtilsLog;

/**
 * Created by Hongsec on 2016-09-05.
 */
public class DB_PartIn extends BaseDB<DB_PartIn> {

    public static final String TABLE_PARTIN = "db_parin";

    private DataBaseHelper mDBHelper = null;
    private SQLiteDatabase mDB_Writer = null;

    protected final static int DB_APP_VERSION = 3;

    public DB_PartIn(Context context) {
        super(context);
    }

    @Override
    public DB_PartIn openDB() {
        mDBHelper = new DataBaseHelper(mContext);
        try {
            mDB_Writer = mDBHelper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public void closeDB() {
        try {
            if (mDB_Writer != null) {
                mDB_Writer.close();
                SQLiteDatabase.releaseMemory();
            }
            if (mDBHelper != null)
                mDBHelper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Cursor fetchAllvalues(String TABLE_NAME) {
        if (mDB_Writer != null)
            return mDB_Writer.query(TABLE_NAME, null, null, null, null, null, null);
        return null;
    }

    @Override
    protected boolean isExistRows(String TABLE_NAME) {
        boolean result = false;
        Cursor fetchAllValues = fetchAllvalues(TABLE_NAME);
        if (fetchAllValues != null) {
            if (fetchAllValues.getCount() > 0) {
                result = true;
            }

            fetchAllValues.close();
        }
        return result;
    }

    @Override
    protected boolean deleteAll(String TABLE_NAME) {
        int id = -1;
        if (mDB_Writer != null) {
            id = mDB_Writer.delete(TABLE_NAME, null, null);
        }
        if (id > 0) {
            return true;
        }
        return false;
    }



    public void InsertOrUpdate(PartInBean partInBean){

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PARTIN.campaign_id,partInBean.getCampaign_id());
        contentValues.put(COLUMN_PARTIN.pid,partInBean.getPid());
        contentValues.put(COLUMN_PARTIN.ifa,partInBean.getIfa());
        contentValues.put(COLUMN_PARTIN.udid,partInBean.getUdid());
        contentValues.put(COLUMN_PARTIN.landingurl,partInBean.getLandingurl());
        contentValues.put(COLUMN_PARTIN.part_id,partInBean.getPart_id());
        contentValues.put(COLUMN_PARTIN.pkg,partInBean.getPkg());
        update("",TABLE_PARTIN,contentValues);
    }

    public PartInBean getPartInItem(String campaign_id){
        if(mDB_Writer!=null){
            Cursor query = mDB_Writer.query(TABLE_PARTIN, null, COLUMN_PARTIN.campaign_id + "=?", new String[]{campaign_id}, null, null, null);
            if(query!=null){
                boolean b = query.moveToFirst();
                if(b){
                    PartInBean partInBean = new PartInBean();
                    partInBean.setCampaign_id(query.getString(query.getColumnIndex(COLUMN_PARTIN.campaign_id)));
                    partInBean.setIfa(query.getString(query.getColumnIndex(COLUMN_PARTIN.ifa)));
                    partInBean.setPart_id(query.getString(query.getColumnIndex(COLUMN_PARTIN.part_id)));
                    partInBean.setUdid(query.getString(query.getColumnIndex(COLUMN_PARTIN.udid)));
                    partInBean.setPid(query.getString(query.getColumnIndex(COLUMN_PARTIN.pid)));
                    partInBean.setLandingurl(query.getString(query.getColumnIndex(COLUMN_PARTIN.landingurl)));
                    partInBean.setPkg(query.getString(query.getColumnIndex(COLUMN_PARTIN.pkg)));
                    return  partInBean;
                }
            }
        }

        return  new PartInBean();
    }


    public PartInBean getPartInItem_pkg(String pkg){
        if(mDB_Writer!=null){
            Cursor query = mDB_Writer.query(TABLE_PARTIN, null, COLUMN_PARTIN.pkg + "=?", new String[]{pkg}, null, null, null);
            if(query!=null){
                boolean b = query.moveToFirst();
                if(b){
                    PartInBean partInBean = new PartInBean();
                    partInBean.setCampaign_id(query.getString(query.getColumnIndex(COLUMN_PARTIN.campaign_id)));
                    partInBean.setIfa(query.getString(query.getColumnIndex(COLUMN_PARTIN.ifa)));
                    partInBean.setPart_id(query.getString(query.getColumnIndex(COLUMN_PARTIN.part_id)));
                    partInBean.setUdid(query.getString(query.getColumnIndex(COLUMN_PARTIN.udid)));
                    partInBean.setPid(query.getString(query.getColumnIndex(COLUMN_PARTIN.pid)));
                    partInBean.setLandingurl(query.getString(query.getColumnIndex(COLUMN_PARTIN.landingurl)));
                    partInBean.setPkg(query.getString(query.getColumnIndex(COLUMN_PARTIN.pkg)));
                    return  partInBean;
                }
            }
        }

        return  new PartInBean();
    }


    public int deletePartInItem(String campaign_id){
        int result = 0;
        if(mDB_Writer!=null){
          result = mDB_Writer.delete(TABLE_PARTIN, COLUMN_PARTIN.campaign_id + "=?", new String[]{campaign_id});
        }

        return  result;
    }

    @Override
    protected boolean update(String do_for_what_log, String table, ContentValues contentValues) {
        long id = -1;

        if (mDB_Writer != null) {
            String adKey = "";
            String columnname = "";
            if (table.equalsIgnoreCase(TABLE_PARTIN)) {
                adKey = contentValues.getAsString(columnname = COLUMN_PARTIN.campaign_id);
                columnname = COLUMN_PARTIN.campaign_id;
            }

            if (checkExist(adKey, table, columnname)) {
                id = mDB_Writer.update(table, contentValues, columnname + "=?", new String[]{adKey});
            } else {
                id = mDB_Writer.insert(table, null, contentValues);
            }
        }

        if (id < 1) {
            return false;
        } else {
            return true;
        }
    }

    private synchronized boolean checkExist(String adKey, String TABLE_NAME, String columnName) {
        boolean result = false;
        if (TextUtils.isEmpty(adKey)) {
            return true;
        }


        if (mDB_Writer != null) {
            Cursor query = mDB_Writer.query(TABLE_NAME, null, columnName + "=?", new String[]{adKey}, null, null, null);
            if (query != null) {
                if (query.getCount() > 0) {

                    result = true;
                }
                query.close();
            }


        }

        return result;
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private static class COLUMN_PARTIN {
        private static final String part_id = "part_id";
        private static final String campaign_id = "campaign_id";
        private static final String udid = "udid";
        private static final String ifa = "ifa";
        private static final String pid = "pid";
        private static final String landingurl = "landingurl";
        private static final  String pkg="pkg";

        private static String column_create() {
            return TABLE_PARTIN + "(" +
                    campaign_id + " TEXT PRIMARY KEY," +
                    part_id + " TEXT DEFAULT ''," +
                    pid + " TEXT DEFAULT ''," +
                    udid + " TEXT DEFAULT ''," +
                    ifa + " TEXT DEFAULT ''," +
                    pkg + " TEXT DEFAULT ''," +
                    landingurl + " TEXT DEFAULT ''" +
                    ");";
        }
    }

    private static class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context context) {
            super(context, TABLE_PARTIN, null, DB_APP_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            UtilsLog.i("onCreateDB");
            db.execSQL("CREATE TABLE IF NOT EXISTS " + COLUMN_PARTIN.column_create());
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion >= newVersion) return;
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTIN);
            onCreate(db);
        }

    }


}
