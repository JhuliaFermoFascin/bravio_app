package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;
import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.GeographicOriginModel;

public class GeographicOriginDAO extends AbstrataDAO {
    public GeographicOriginDAO(Context context) { helper = new DPOpenHelper(context); }
    public long insert(final GeographicOriginModel model) {
        long result = -1;
        try { Open();
            ContentValues values = new ContentValues();
            values.put(GeographicOriginModel.COLUMN_COUNTRY, model.getCountry());
            values.put(GeographicOriginModel.COLUMN_REGION, model.getRegion());
            result = db.insert(GeographicOriginModel.TABLE_NAME, null, values);
        } finally { Close(); }
        return result;
    }
}
