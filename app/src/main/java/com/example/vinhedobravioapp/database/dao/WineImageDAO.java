package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.WineImageModel;

public class WineImageDAO extends AbstrataDAO {
    public WineImageDAO(Context context) { helper = new DPOpenHelper(context); }

    public long insert(final WineImageModel model) {
        long result = -1;
        try { Open();
            ContentValues values = new ContentValues();
            values.put(WineImageModel.COLUMN_WINE_ID, model.getWineId());
            values.put(WineImageModel.COLUMN_IMAGE_BASE64, model.getImageBase64());
            result = db.insert(WineImageModel.TABLE_NAME, null, values);
        } finally { Close(); }
        return result;
    }

    public WineImageModel getByWineId(long wineId) {
        WineImageModel model = null;
        try { Open();
            Cursor cursor = db.query(WineImageModel.TABLE_NAME, null,
                WineImageModel.COLUMN_WINE_ID + " = ?",
                new String[]{String.valueOf(wineId)},
                null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                model = new WineImageModel();
                model.setWineId(cursor.getLong(cursor.getColumnIndexOrThrow(WineImageModel.COLUMN_WINE_ID)));
                model.setImageBase64(cursor.getString(cursor.getColumnIndexOrThrow(WineImageModel.COLUMN_IMAGE_BASE64)));
                cursor.close();
            }
        } finally { Close(); }
        return model;
    }

    public WineImageModel getById(long wineId) {
        return getByWineId(wineId);
    }

    public java.util.List<WineImageModel> getAll() {
        java.util.List<WineImageModel> list = new java.util.ArrayList<>();
        try { Open();
            Cursor cursor = db.query(WineImageModel.TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    WineImageModel model = new WineImageModel();
                    model.setWineId(cursor.getLong(cursor.getColumnIndexOrThrow(WineImageModel.COLUMN_WINE_ID)));
                    model.setImageBase64(cursor.getString(cursor.getColumnIndexOrThrow(WineImageModel.COLUMN_IMAGE_BASE64)));
                    list.add(model);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally { Close(); }
        return list;
    }

    public int update(WineImageModel model) {
        int rows = 0;
        try { Open();
            ContentValues values = new ContentValues();
            values.put(WineImageModel.COLUMN_IMAGE_BASE64, model.getImageBase64());
            rows = db.update(WineImageModel.TABLE_NAME, values,
                WineImageModel.COLUMN_WINE_ID + " = ?",
                new String[]{String.valueOf(model.getWineId())});
        } finally { Close(); }
        return rows;
    }

    public int deleteByWineId(long wineId) {
        int rows = 0;
        try {
            Open();
            rows = db.delete(
                    WineImageModel.TABLE_NAME,
                    WineImageModel.COLUMN_WINE_ID + " = ?",
                    new String[]{String.valueOf(wineId)}
            );
        } finally {
            Close();
        }
        return rows;
    }


    public int delete(long wineId) {
        int rows = 0;
        try { Open();
            rows = db.delete(WineImageModel.TABLE_NAME,
                WineImageModel.COLUMN_WINE_ID + " = ?",
                new String[]{String.valueOf(wineId)});
        } finally { Close(); }
        return rows;
    }
}
