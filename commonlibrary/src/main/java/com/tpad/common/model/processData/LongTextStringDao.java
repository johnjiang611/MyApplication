package com.tpad.common.model.processData;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.tpad.common.model.db.ormlite.OrmLiteBaseDao;
import com.tpad.common.model.processData.entities.LongTextString;

import java.sql.SQLException;

/**
 *
 * @author jone.sun on 2015/3/24.
 */
public class LongTextStringDao extends OrmLiteBaseDao<LongTextString> {
    public static final String KEY_COLUMN_KEY = "key";
    private static LongTextStringDao instance;
    public static LongTextStringDao getInstance(Context context){
        if(instance == null){
            instance = new LongTextStringDao(context);
        }
        return instance;
    }

    private LongTextStringDao(Context context) {
        super(context);
    }

    public boolean save(String key, String value){
        Dao.CreateOrUpdateStatus status = super.createOrUpdate(new LongTextString(key, value));
        if(status != null && status.getNumLinesChanged() > 0){
            return true;
        }
        return false;
    }

    public String getValueByKey(String key) {
        String value = null;
        LongTextString longTextString = super.queryByColumn(KEY_COLUMN_KEY, key);
        if(longTextString != null){
            value = longTextString.getValue();
        }
        return value;
    }

    public int delValueByKey(String key) {
        try{
            DeleteBuilder<LongTextString, Integer> deleteBuilder = getDao().deleteBuilder();
            deleteBuilder.where().eq(KEY_COLUMN_KEY, key);
            return deleteBuilder.delete();
        }
        catch (SQLException e){
            Log.e("delValueByKey", "" ,e);
        }
        return 0;
    }
}
