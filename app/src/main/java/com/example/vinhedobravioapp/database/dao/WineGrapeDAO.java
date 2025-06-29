package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;
import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.GrapeModel;
import com.example.vinhedobravioapp.database.model.WineGrapeModel;

import java.util.ArrayList;
import java.util.List;

public class WineGrapeDAO extends AbstrataDAO {
    public WineGrapeDAO(Context context) { helper = new DPOpenHelper(context); }
    public long insert(final WineGrapeModel model) {
        long result = -1;
        try { Open();
            ContentValues values = new ContentValues();
            values.put(WineGrapeModel.COLUMN_WINE_ID, model.getWineId());
            values.put(WineGrapeModel.COLUMN_GRAPE_ID, model.getGrapeId());
            result = db.insert(WineGrapeModel.TABLE_NAME, null, values);
        } finally { Close(); }
        return result;
    }

    public WineGrapeModel getById(long wineId, long grapeId) {
        WineGrapeModel model = null;
        try { Open();
            android.database.Cursor cursor = db.query(WineGrapeModel.TABLE_NAME, null,
                WineGrapeModel.COLUMN_WINE_ID + " = ? AND " + WineGrapeModel.COLUMN_GRAPE_ID + " = ?",
                new String[]{String.valueOf(wineId), String.valueOf(grapeId)},
                null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                model = new WineGrapeModel();
                model.setWineId(cursor.getLong(cursor.getColumnIndexOrThrow(WineGrapeModel.COLUMN_WINE_ID)));
                model.setGrapeId(cursor.getLong(cursor.getColumnIndexOrThrow(WineGrapeModel.COLUMN_GRAPE_ID)));
                cursor.close();
            }
        } finally { Close(); }
        return model;
    }

    public java.util.List<WineGrapeModel> getAll() {
        java.util.List<WineGrapeModel> list = new java.util.ArrayList<>();
        try { Open();
            android.database.Cursor cursor = db.query(WineGrapeModel.TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    WineGrapeModel model = new WineGrapeModel();
                    model.setWineId(cursor.getLong(cursor.getColumnIndexOrThrow(WineGrapeModel.COLUMN_WINE_ID)));
                    model.setGrapeId(cursor.getLong(cursor.getColumnIndexOrThrow(WineGrapeModel.COLUMN_GRAPE_ID)));
                    list.add(model);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally { Close(); }
        return list;
    }

    public List<Long> getGrapeIdsByWineId(long wineId) {
        List<Long> grapeIds = new ArrayList<>();
        try { Open();
            android.database.Cursor cursor = db.query(
                    WineGrapeModel.TABLE_NAME,
                    new String[]{WineGrapeModel.COLUMN_GRAPE_ID},
                    WineGrapeModel.COLUMN_WINE_ID + " = ?",
                    new String[]{String.valueOf(wineId)},
                    null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    grapeIds.add(cursor.getLong(cursor.getColumnIndexOrThrow(WineGrapeModel.COLUMN_GRAPE_ID)));
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally { Close(); }
        return grapeIds;
    }


    public int update(WineGrapeModel model) {
        int rows = 0;
        try { Open();
            android.content.ContentValues values = new android.content.ContentValues();
            values.put(WineGrapeModel.COLUMN_WINE_ID, model.getWineId());
            values.put(WineGrapeModel.COLUMN_GRAPE_ID, model.getGrapeId());
            rows = db.update(WineGrapeModel.TABLE_NAME, values,
                WineGrapeModel.COLUMN_WINE_ID + " = ? AND " + WineGrapeModel.COLUMN_GRAPE_ID + " = ?",
                new String[]{String.valueOf(model.getWineId()), String.valueOf(model.getGrapeId())});
        } finally { Close(); }
        return rows;
    }

    public int deleteByWineId(long wineId) {
        int rows = 0;
        try {
            Open();
            rows = db.delete(
                    WineGrapeModel.TABLE_NAME,
                    WineGrapeModel.COLUMN_WINE_ID + " = ?",
                    new String[]{String.valueOf(wineId)}
            );
        } finally {
            Close();
        }
        return rows;
    }


    public int delete(long wineId, long grapeId) {
        int rows = 0;
        try { Open();
            rows = db.delete(WineGrapeModel.TABLE_NAME,
                WineGrapeModel.COLUMN_WINE_ID + " = ? AND " + WineGrapeModel.COLUMN_GRAPE_ID + " = ?",
                new String[]{String.valueOf(wineId), String.valueOf(grapeId)});
        } finally { Close(); }
        return rows;
    }
    public List<String> getGrapeNamesByWineId(long wineId, Context context) {
    List<String> grapeNames = new ArrayList<>();
    GrapeDAO grapeDAO = new GrapeDAO(context);

    try { Open();
        android.database.Cursor cursor = db.query(
                WineGrapeModel.TABLE_NAME,
                new String[]{WineGrapeModel.COLUMN_GRAPE_ID},
                WineGrapeModel.COLUMN_WINE_ID + " = ?",
                new String[]{String.valueOf(wineId)},
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                long grapeId = cursor.getLong(cursor.getColumnIndexOrThrow(WineGrapeModel.COLUMN_GRAPE_ID));
                GrapeModel grape = grapeDAO.getById(grapeId);
                if (grape != null) {
                    grapeNames.add(grape.getName()); // Supondo que o nome da uva seja retornado por getUva()
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
    } finally { Close(); }

    return grapeNames;
}

}
