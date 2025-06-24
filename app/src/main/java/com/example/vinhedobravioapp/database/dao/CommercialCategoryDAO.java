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

    public CommercialCategoryModel getById(long id) {
        CommercialCategoryModel model = null;
        try { Open();
            android.database.Cursor cursor = db.query(CommercialCategoryModel.TABLE_NAME, null,
                CommercialCategoryModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                model = new CommercialCategoryModel();
                model.setCategoryId(cursor.getLong(cursor.getColumnIndexOrThrow(CommercialCategoryModel.COLUMN_ID)));
                model.setName(cursor.getString(cursor.getColumnIndexOrThrow(CommercialCategoryModel.COLUMN_NAME)));
                cursor.close();
            }
        } finally { Close(); }
        return model;
    }

    public java.util.List<CommercialCategoryModel> getAll() {
        java.util.List<CommercialCategoryModel> list = new java.util.ArrayList<>();
        try { Open();
            android.database.Cursor cursor = db.query(CommercialCategoryModel.TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    CommercialCategoryModel model = new CommercialCategoryModel();
                    model.setCategoryId(cursor.getLong(cursor.getColumnIndexOrThrow(CommercialCategoryModel.COLUMN_ID)));
                    model.setName(cursor.getString(cursor.getColumnIndexOrThrow(CommercialCategoryModel.COLUMN_NAME)));
                    list.add(model);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally { Close(); }
        return list;
    }

    public int update(CommercialCategoryModel model) {
        int rows = 0;
        try { Open();
            android.content.ContentValues values = new android.content.ContentValues();
            values.put(CommercialCategoryModel.COLUMN_NAME, model.getName());
            rows = db.update(CommercialCategoryModel.TABLE_NAME, values,
                CommercialCategoryModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(model.getCategoryId())});
        } finally { Close(); }
        return rows;
    }

    public int delete(long id) {
        int rows = 0;
        try { Open();
            rows = db.delete(CommercialCategoryModel.TABLE_NAME,
                CommercialCategoryModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        } finally { Close(); }
        return rows;
    }
}
