package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;
import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.WineGrapeModel;

public class WineGrapeDAO extends AbstrataDAO {
    public WineGrapeDAO(Context context) { helper = new DPOpenHelper(context); }
    public long insert(final WineGrapeModel model) {
        long result = -1;
        try { Open();
            ContentValues values = new ContentValues();
            values.put(WineGrapeModel.COLUMN_WINE_ID, model.getWineId());
            values.put(WineGrapeModel.COLUMN_GRAPE_ID, model.getGrapeId());
            result = db.insert(WineGrapeModel.TABLE_NAME, null, values);
        } finally { Close(); }
        return result;
    }
}
