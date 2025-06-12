package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;
import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.WineryModel;

public class WineryDAO extends AbstrataDAO {
    public WineryDAO(Context context) { helper = new DPOpenHelper(context); }
    public long insert(final WineryModel model) {
        long result = -1;
        try { Open();
            ContentValues values = new ContentValues();
            values.put(WineryModel.COLUMN_NAME, model.getName());
            result = db.insert(WineryModel.TABLE_NAME, null, values);
        } finally { Close(); }
        return result;
    }
}
