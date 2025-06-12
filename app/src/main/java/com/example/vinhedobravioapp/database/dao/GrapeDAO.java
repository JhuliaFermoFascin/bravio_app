package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;
import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.GrapeModel;

public class GrapeDAO extends AbstrataDAO {
    public GrapeDAO(Context context) { helper = new DPOpenHelper(context); }
    public long insert(final GrapeModel model) {
        long result = -1;
        try { Open();
            ContentValues values = new ContentValues();
            values.put(GrapeModel.COLUMN_NAME, model.getName());
            result = db.insert(GrapeModel.TABLE_NAME, null, values);
        } finally { Close(); }
        return result;
    }
}
