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

    public WineryModel getById(long id) {
        WineryModel model = null;
        try { Open();
            android.database.Cursor cursor = db.query(WineryModel.TABLE_NAME, null,
                WineryModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                model = new WineryModel();
                model.setWineryId(cursor.getLong(cursor.getColumnIndexOrThrow(WineryModel.COLUMN_ID)));
                model.setName(cursor.getString(cursor.getColumnIndexOrThrow(WineryModel.COLUMN_NAME)));
                cursor.close();
            }
        } finally { Close(); }
        return model;
    }

    public java.util.List<WineryModel> getAll() {
        java.util.List<WineryModel> list = new java.util.ArrayList<>();
        try { Open();
            android.database.Cursor cursor = db.query(WineryModel.TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    WineryModel model = new WineryModel();
                    model.setWineryId(cursor.getLong(cursor.getColumnIndexOrThrow(WineryModel.COLUMN_ID)));
                    model.setName(cursor.getString(cursor.getColumnIndexOrThrow(WineryModel.COLUMN_NAME)));
                    list.add(model);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally { Close(); }
        return list;
    }

    public int update(WineryModel model) {
        int rows = 0;
        try { Open();
            android.content.ContentValues values = new android.content.ContentValues();
            values.put(WineryModel.COLUMN_NAME, model.getName());
            rows = db.update(WineryModel.TABLE_NAME, values,
                WineryModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(model.getWineryId())});
        } finally { Close(); }
        return rows;
    }

    public int delete(long id) {
        int rows = 0;
        try { Open();
            rows = db.delete(WineryModel.TABLE_NAME,
                WineryModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        } finally { Close(); }
        return rows;
    }
}
