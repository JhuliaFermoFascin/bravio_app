package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.CompositionTypeModel;

import java.util.ArrayList;
import java.util.List;

public class CompositionTypeDAO extends AbstrataDAO {

    public CompositionTypeDAO(Context context) { helper = new DPOpenHelper(context); }

    public long insert(final CompositionTypeModel model) {
        long result = -1;
        try {
            Open();
            ContentValues values = new ContentValues();
            values.put(CompositionTypeModel.COLUMN_NAME, model.getCompositionName());
            result = db.insert(CompositionTypeModel.TABLE_NAME, null, values);
        } finally {
            Close();
        }
        return result;
    }

    public CompositionTypeModel getById(long id) {
        CompositionTypeModel model = null;
        try {
            Open();
            Cursor cursor = db.query(CompositionTypeModel.TABLE_NAME, null,
                    CompositionTypeModel.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(id)}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                model = new CompositionTypeModel();
                model.setCompositionTypeId(cursor.getLong(cursor.getColumnIndexOrThrow(CompositionTypeModel.COLUMN_ID)));
                model.setCompositionName(cursor.getString(cursor.getColumnIndexOrThrow(CompositionTypeModel.COLUMN_NAME)));
                cursor.close();
            }
        } finally {
            Close();
        }
        return model;
    }

    public List<CompositionTypeModel> getAll() {
        List<CompositionTypeModel> list = new ArrayList<>();
        try {
            Open();
            Cursor cursor = db.query(CompositionTypeModel.TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    CompositionTypeModel model = new CompositionTypeModel();
                    model.setCompositionTypeId(cursor.getLong(cursor.getColumnIndexOrThrow(CompositionTypeModel.COLUMN_ID)));
                    model.setCompositionName(cursor.getString(cursor.getColumnIndexOrThrow(CompositionTypeModel.COLUMN_NAME)));
                    list.add(model);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally {
            Close();
        }
        return list;
    }

    public int update(CompositionTypeModel model) {
        int rows = 0;
        try {
            Open();
            ContentValues values = new ContentValues();
            values.put(CompositionTypeModel.COLUMN_NAME, model.getCompositionName());
            rows = db.update(CompositionTypeModel.TABLE_NAME, values,
                    CompositionTypeModel.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(model.getCompositionTypeId())});
        } finally {
            Close();
        }
        return rows;
    }

    public int delete(long id) {
        int rows = 0;
        try {
            Open();
            rows = db.delete(CompositionTypeModel.TABLE_NAME,
                    CompositionTypeModel.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(id)});
        } finally {
            Close();
        }
        return rows;
    }
}
