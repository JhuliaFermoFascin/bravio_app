package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.FoodPairingModel;

import java.util.ArrayList;
import java.util.List;

public class FoodPairingDAO extends AbstrataDAO{
    public FoodPairingDAO(Context context) {
        helper = new DPOpenHelper(context);
    }

    public long insert(FoodPairingModel model) {
        long result = -1;
        try {
            Open();
            ContentValues values = new ContentValues();
            values.put(FoodPairingModel.COLUMN_NAME, model.getName());
            result = db.insert(FoodPairingModel.TABLE_NAME, null, values);
        } finally {
            Close();
        }
        return result;
    }

    public long getIdByName(String name) {
        long id = -1;
        try {
            Open();
            Cursor cursor = db.query(FoodPairingModel.TABLE_NAME, null,
                    FoodPairingModel.COLUMN_NAME + " = ?",
                    new String[]{name},
                    null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                id = cursor.getLong(cursor.getColumnIndexOrThrow(FoodPairingModel.COLUMN_ID));
                cursor.close();
            }
        } finally {
            Close();
        }
        return id;
    }

    public List<FoodPairingModel> getAll() {
        List<FoodPairingModel> list = new ArrayList<>();
        try {
            Open();
            Cursor cursor = db.query(FoodPairingModel.TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    FoodPairingModel model = new FoodPairingModel();
                    model.setFoodPairingId(cursor.getLong(cursor.getColumnIndexOrThrow(FoodPairingModel.COLUMN_ID)));
                    model.setName(cursor.getString(cursor.getColumnIndexOrThrow(FoodPairingModel.COLUMN_NAME)));
                    list.add(model);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally {
            Close();
        }
        return list;
    }

    public FoodPairingModel getById(long id) {
        FoodPairingModel model = null;
        try {
            Open();
            Cursor cursor = db.query(FoodPairingModel.TABLE_NAME, null,
                    FoodPairingModel.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(id)},
                    null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                model = new FoodPairingModel();
                model.setFoodPairingId(cursor.getLong(cursor.getColumnIndexOrThrow(FoodPairingModel.COLUMN_ID)));
                model.setName(cursor.getString(cursor.getColumnIndexOrThrow(FoodPairingModel.COLUMN_NAME)));
                cursor.close();
            }
        } finally {
            Close();
        }
        return model;
    }

}
