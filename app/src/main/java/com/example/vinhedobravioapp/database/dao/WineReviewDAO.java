package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.WineReviewModel;

public class WineReviewDAO extends AbstrataDAO {
    public WineReviewDAO(Context context) { helper = new DPOpenHelper(context); }
    public long insert(final WineReviewModel model) {
        long result = -1;
        try { Open();
            ContentValues values = new ContentValues();
            values.put(WineReviewModel.COLUMN_WINE_ID, model.getWineId());
            values.put(WineReviewModel.COLUMN_REVIEW_TEXT, model.getReviewText());
            values.put(WineReviewModel.COLUMN_RATING, model.getRating());
            result = db.insert(WineReviewModel.TABLE_NAME, null, values);
        } finally { Close(); }
        return result;
    }

    public WineReviewModel getById(long id) {
        WineReviewModel model = null;
        try { Open();
            android.database.Cursor cursor = db.query(WineReviewModel.TABLE_NAME, null,
                WineReviewModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                model = new WineReviewModel();
                model.setReviewId(cursor.getLong(cursor.getColumnIndexOrThrow(WineReviewModel.COLUMN_ID)));
                model.setWineId(cursor.getLong(cursor.getColumnIndexOrThrow(WineReviewModel.COLUMN_WINE_ID)));
                model.setReviewText(cursor.getString(cursor.getColumnIndexOrThrow(WineReviewModel.COLUMN_REVIEW_TEXT)));
                model.setRating(cursor.getDouble(cursor.getColumnIndexOrThrow(WineReviewModel.COLUMN_RATING)));
                cursor.close();
            }
        } finally { Close(); }
        return model;
    }

    public WineReviewModel getByWineId(long wineId) {
        WineReviewModel model = null;
        try {
            Open();
            Cursor cursor = db.query(
                    WineReviewModel.TABLE_NAME,
                    null,
                    WineReviewModel.COLUMN_WINE_ID + " = ?",
                    new String[]{ String.valueOf(wineId) },
                    null, null, null
            );
            if (cursor != null && cursor.moveToFirst()) {
                model = new WineReviewModel();
                model.setReviewId(cursor.getLong(
                        cursor.getColumnIndexOrThrow(WineReviewModel.COLUMN_ID)
                ));
                model.setWineId(cursor.getLong(
                        cursor.getColumnIndexOrThrow(WineReviewModel.COLUMN_WINE_ID)
                ));
                model.setReviewText(cursor.getString(
                        cursor.getColumnIndexOrThrow(WineReviewModel.COLUMN_REVIEW_TEXT)
                ));
                model.setRating(cursor.getDouble(
                        cursor.getColumnIndexOrThrow(WineReviewModel.COLUMN_RATING)
                ));
                cursor.close();
            }
        } finally {
            Close();
        }
        return model;
    }

    public java.util.List<WineReviewModel> getAll() {
        java.util.List<WineReviewModel> list = new java.util.ArrayList<>();
        try { Open();
            android.database.Cursor cursor = db.query(WineReviewModel.TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    WineReviewModel model = new WineReviewModel();
                    model.setReviewId(cursor.getLong(cursor.getColumnIndexOrThrow(WineReviewModel.COLUMN_ID)));
                    model.setWineId(cursor.getLong(cursor.getColumnIndexOrThrow(WineReviewModel.COLUMN_WINE_ID)));
                    model.setReviewText(cursor.getString(cursor.getColumnIndexOrThrow(WineReviewModel.COLUMN_REVIEW_TEXT)));
                    model.setRating(cursor.getDouble(cursor.getColumnIndexOrThrow(WineReviewModel.COLUMN_RATING)));
                    list.add(model);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally { Close(); }
        return list;
    }

    public int update(WineReviewModel model) {
        int rows = 0;
        try { Open();
            android.content.ContentValues values = new android.content.ContentValues();
            values.put(WineReviewModel.COLUMN_WINE_ID, model.getWineId());
            values.put(WineReviewModel.COLUMN_REVIEW_TEXT, model.getReviewText());
            values.put(WineReviewModel.COLUMN_RATING, model.getRating());
            rows = db.update(WineReviewModel.TABLE_NAME, values,
                WineReviewModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(model.getReviewId())});
        } finally { Close(); }
        return rows;
    }

    public int deleteByWineId(long wineId) {
        int rows = 0;
        try {
            Open();
            rows = db.delete(
                    WineReviewModel.TABLE_NAME,
                    WineReviewModel.COLUMN_WINE_ID + " = ?",
                    new String[]{String.valueOf(wineId)}
            );
        } finally {
            Close();
        }
        return rows;
    }


    public int delete(long id) {
        int rows = 0;
        try { Open();
            rows = db.delete(WineReviewModel.TABLE_NAME,
                WineReviewModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        } finally { Close(); }
        return rows;
    }
}
