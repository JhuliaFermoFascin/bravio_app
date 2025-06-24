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

    public GrapeModel getById(long id) {
        GrapeModel model = null;
        try { Open();
            android.database.Cursor cursor = db.query(GrapeModel.TABLE_NAME, null,
                GrapeModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                model = new GrapeModel();
                model.setGrapeId(cursor.getLong(cursor.getColumnIndexOrThrow(GrapeModel.COLUMN_ID)));
                model.setName(cursor.getString(cursor.getColumnIndexOrThrow(GrapeModel.COLUMN_NAME)));
                cursor.close();
            }
        } finally { Close(); }
        return model;
    }

    public java.util.List<GrapeModel> getAll() {
        java.util.List<GrapeModel> list = new java.util.ArrayList<>();
        try { Open();
            android.database.Cursor cursor = db.query(GrapeModel.TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    GrapeModel model = new GrapeModel();
                    model.setGrapeId(cursor.getLong(cursor.getColumnIndexOrThrow(GrapeModel.COLUMN_ID)));
                    model.setName(cursor.getString(cursor.getColumnIndexOrThrow(GrapeModel.COLUMN_NAME)));
                    list.add(model);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally { Close(); }
        return list;
    }

    public int update(GrapeModel model) {
        int rows = 0;
        try { Open();
            android.content.ContentValues values = new android.content.ContentValues();
            values.put(GrapeModel.COLUMN_NAME, model.getName());
            rows = db.update(GrapeModel.TABLE_NAME, values,
                GrapeModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(model.getGrapeId())});
        } finally { Close(); }
        return rows;
    }

    public int delete(long id) {
        int rows = 0;
        try { Open();
            rows = db.delete(GrapeModel.TABLE_NAME,
                GrapeModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        } finally { Close(); }
        return rows;
    }
}
