package genius.baselib.frame;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * Created by chief on 2015-12-22.
 */
public abstract class BaseDB<T> extends ContentProvider{

    protected Context mContext;

    public BaseDB(){}

    public BaseDB(Context context){
        mContext=context;
    }

    public abstract T openDB() ;

    public abstract void closeDB();

    protected abstract Cursor fetchAllvalues(String TABLE_NAME);

    protected abstract boolean isExistRows(String TABLE_NAME);

    protected abstract boolean deleteAll(String TABLE_NAME);

    protected abstract boolean update(String do_for_what_log,String TABLE_NAME,ContentValues contentValues);

    protected  boolean Parse_Str2bool(String str){
        boolean result=false;
        try {
            result=Boolean.parseBoolean(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
