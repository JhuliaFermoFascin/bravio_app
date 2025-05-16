package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;

import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.WineTypeModel;

public class WineTypeDAO extends AbstrataDAO {

    public WineTypeDAO(Context context) {
        helper = new DPOpenHelper(context);
    }

    public long insert(final WineTypeModel wineTypeModel) {
        long result = -1;
        try {
            Open();
            ContentValues values = new ContentValues();
            values.put(WineTypeModel.COLUMN_ID, wineTypeModel.getWineTypeId());
            values.put(WineTypeModel.COLUMN_TYPE_NAME, wineTypeModel.getTypeName());
            result = db.insert(WineTypeModel.TABLE_NAME, null, values);
        } finally {
            Close();
        }
        return result;
    }
}
