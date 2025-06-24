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
            values.put(WineTypeModel.COLUMN_TYPE_NAME, wineTypeModel.getTypeName());
            result = db.insert(WineTypeModel.TABLE_NAME, null, values);
        } finally {
            Close();
        }
        return result;
    }

    public WineTypeModel getById(long id) {
        WineTypeModel model = null;
        try {
            Open();
            android.database.Cursor cursor = db.query(WineTypeModel.TABLE_NAME, null,
                WineTypeModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                model = new WineTypeModel();
                model.setWineTypeId(cursor.getLong(cursor.getColumnIndexOrThrow(WineTypeModel.COLUMN_ID)));
                model.setTypeName(cursor.getString(cursor.getColumnIndexOrThrow(WineTypeModel.COLUMN_TYPE_NAME)));
                cursor.close();
            }
        } finally {
            Close();
        }
        return model;
    }

    public java.util.List<WineTypeModel> getAll() {
        java.util.List<WineTypeModel> list = new java.util.ArrayList<>();
        try {
            Open();
            android.database.Cursor cursor = db.query(WineTypeModel.TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    WineTypeModel model = new WineTypeModel();
                    model.setWineTypeId(cursor.getLong(cursor.getColumnIndexOrThrow(WineTypeModel.COLUMN_ID)));
                    model.setTypeName(cursor.getString(cursor.getColumnIndexOrThrow(WineTypeModel.COLUMN_TYPE_NAME)));
                    list.add(model);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally {
            Close();
        }
        return list;
    }

    public int update(WineTypeModel model) {
        int rows = 0;
        try {
            Open();
            android.content.ContentValues values = new android.content.ContentValues();
            values.put(WineTypeModel.COLUMN_TYPE_NAME, model.getTypeName());
            rows = db.update(WineTypeModel.TABLE_NAME, values,
                WineTypeModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(model.getWineTypeId())});
        } finally {
            Close();
        }
        return rows;
    }

    public int delete(long id) {
        int rows = 0;
        try {
            Open();
            rows = db.delete(WineTypeModel.TABLE_NAME,
                WineTypeModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        } finally {
            Close();
        }
        return rows;
    }
}
