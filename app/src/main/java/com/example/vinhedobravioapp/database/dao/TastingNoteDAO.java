package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.TastingNoteModel;

public class TastingNoteDAO extends AbstrataDAO {

    public TastingNoteDAO(Context context) {
        helper = new DPOpenHelper(context);
    }

    public long insert(final TastingNoteModel tastingNoteModel) {
        long result = -1;
        try {
            Open();
            ContentValues values = new ContentValues();
            values.put(TastingNoteModel.COLUMN_NOTE, tastingNoteModel.getNote());
            result = db.insert(TastingNoteModel.TABLE_NAME, null, values);
        } finally {
            Close();
        }
        return result;
    }

    public TastingNoteModel getById(long id) {
        TastingNoteModel model = null;
        try {
            Open();
            android.database.Cursor cursor = db.query(TastingNoteModel.TABLE_NAME, null,
                TastingNoteModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                model = new TastingNoteModel();
                model.setTastingNoteId(cursor.getLong(cursor.getColumnIndexOrThrow(TastingNoteModel.COLUMN_ID)));
                model.setNote(cursor.getString(cursor.getColumnIndexOrThrow(TastingNoteModel.COLUMN_NOTE)));
                cursor.close();
            }
        } finally {
            Close();
        }
        return model;
    }

    public long getIdByNoteText(String note) {
        long id = -1;
        try {
            Open();
            Cursor cursor = db.query(TastingNoteModel.TABLE_NAME, null,
                    TastingNoteModel.COLUMN_NOTE + " = ?",
                    new String[]{note},
                    null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                id = cursor.getLong(cursor.getColumnIndexOrThrow(TastingNoteModel.COLUMN_ID));
                cursor.close();
            }
        } finally {
            Close();
        }
        return id;
    }

    public TastingNoteModel getByNoteText(String note) {
        TastingNoteModel model = null;
        try {
            Open();
            Cursor cursor = db.query(TastingNoteModel.TABLE_NAME, null,
                    TastingNoteModel.COLUMN_NOTE + " = ?",
                    new String[]{note},
                    null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                model = new TastingNoteModel();
                model.setTastingNoteId(cursor.getLong(cursor.getColumnIndexOrThrow(TastingNoteModel.COLUMN_ID)));
                model.setNote(cursor.getString(cursor.getColumnIndexOrThrow(TastingNoteModel.COLUMN_NOTE)));
                cursor.close();
            }
        } finally {
            Close();
        }
        return model;
    }

    public java.util.List<TastingNoteModel> getAll() {
        java.util.List<TastingNoteModel> list = new java.util.ArrayList<>();
        try {
            Open();
            android.database.Cursor cursor = db.query(TastingNoteModel.TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    TastingNoteModel model = new TastingNoteModel();
                    model.setTastingNoteId(cursor.getLong(cursor.getColumnIndexOrThrow(TastingNoteModel.COLUMN_ID)));
                    model.setNote(cursor.getString(cursor.getColumnIndexOrThrow(TastingNoteModel.COLUMN_NOTE)));
                    list.add(model);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally {
            Close();
        }
        return list;
    }

    public int update(TastingNoteModel model) {
        int rows = 0;
        try {
            Open();
            android.content.ContentValues values = new android.content.ContentValues();
            values.put(TastingNoteModel.COLUMN_NOTE, model.getNote());
            rows = db.update(TastingNoteModel.TABLE_NAME, values,
                TastingNoteModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(model.getTastingNoteId())});
        } finally {
            Close();
        }
        return rows;
    }

    public int delete(long id) {
        int rows = 0;
        try {
            Open();
            rows = db.delete(TastingNoteModel.TABLE_NAME,
                TastingNoteModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        } finally {
            Close();
        }
        return rows;
    }
}
