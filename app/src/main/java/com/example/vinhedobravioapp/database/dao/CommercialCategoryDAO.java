package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;
import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.CommercialCategoryModel;

public class CommercialCategoryDAO extends AbstrataDAO {
    public CommercialCategoryDAO(Context context) { helper = new DPOpenHelper(context); }
    public long insert(final CommercialCategoryModel model) {
        long result = -1;
        try { Open();
            ContentValues values = new ContentValues();
            values.put(CommercialCategoryModel.COLUMN_NAME, model.getName());
            result = db.insert(CommercialCategoryModel.TABLE_NAME, null, values);
        } finally { Close(); }
        return result;
    }
}
