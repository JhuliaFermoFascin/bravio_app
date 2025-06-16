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

    public GeographicOriginModel getById(long id) {
        GeographicOriginModel model = null;
        try { Open();
            android.database.Cursor cursor = db.query(GeographicOriginModel.TABLE_NAME, null,
                GeographicOriginModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                model = new GeographicOriginModel();
                model.setOriginId(cursor.getLong(cursor.getColumnIndexOrThrow(GeographicOriginModel.COLUMN_ID)));
                model.setCountry(cursor.getString(cursor.getColumnIndexOrThrow(GeographicOriginModel.COLUMN_COUNTRY)));
                model.setRegion(cursor.getString(cursor.getColumnIndexOrThrow(GeographicOriginModel.COLUMN_REGION)));
                cursor.close();
            }
        } finally { Close(); }
        return model;
    }

    public java.util.List<GeographicOriginModel> getAll() {
        java.util.List<GeographicOriginModel> list = new java.util.ArrayList<>();
        try { Open();
            android.database.Cursor cursor = db.query(GeographicOriginModel.TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    GeographicOriginModel model = new GeographicOriginModel();
                    model.setOriginId(cursor.getLong(cursor.getColumnIndexOrThrow(GeographicOriginModel.COLUMN_ID)));
                    model.setCountry(cursor.getString(cursor.getColumnIndexOrThrow(GeographicOriginModel.COLUMN_COUNTRY)));
                    model.setRegion(cursor.getString(cursor.getColumnIndexOrThrow(GeographicOriginModel.COLUMN_REGION)));
                    list.add(model);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally { Close(); }
        return list;
    }

    public int update(GeographicOriginModel model) {
        int rows = 0;
        try { Open();
            android.content.ContentValues values = new android.content.ContentValues();
            values.put(GeographicOriginModel.COLUMN_COUNTRY, model.getCountry());
            values.put(GeographicOriginModel.COLUMN_REGION, model.getRegion());
            rows = db.update(GeographicOriginModel.TABLE_NAME, values,
                GeographicOriginModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(model.getOriginId())});
        } finally { Close(); }
        return rows;
    }

    public int delete(long id) {
        int rows = 0;
        try { Open();
            rows = db.delete(GeographicOriginModel.TABLE_NAME,
                GeographicOriginModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        } finally { Close(); }
        return rows;
    }
}
