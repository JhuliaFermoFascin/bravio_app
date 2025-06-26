package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.VisitModel;

import java.util.ArrayList;
import java.util.List;

public class VisitDAO extends AbstrataDAO {

    public VisitDAO(Context context) {
        helper = new DPOpenHelper(context);
    }

    public long insert(final VisitModel visitModel) {
        long result = -1;
        try {
            Open();
            ContentValues values = new ContentValues();
            values.put(VisitModel.COLUMN_ID, visitModel.getVisitId());
            values.put(VisitModel.COLUMN_CUSTOMER_ID, visitModel.getCustomerId());
            values.put(VisitModel.COLUMN_DATE_TIME, visitModel.getDateTime());
            values.put(VisitModel.COLUMN_LOCATION, visitModel.getLocation());
            values.put(VisitModel.COLUMN_WINES, visitModel.getWines());
            values.put(VisitModel.COLUMN_DESCRIPTION, visitModel.getDescription()); // NOVO
            values.put(VisitModel.COLUMN_USER_ID, visitModel.getUserId());
            result = db.insert(VisitModel.TABLE_NAME, null, values);
        } finally {
            Close();
        }
        return result;
    }

    public VisitModel getById(long id) {
        VisitModel model = null;
        try {
            Open();
            Cursor cursor = db.query(VisitModel.TABLE_NAME, null,
                    VisitModel.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(id)},
                    null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                model = new VisitModel();
                model.setVisitId(cursor.getLong(cursor.getColumnIndexOrThrow(VisitModel.COLUMN_ID)));
                model.setCustomerId(cursor.getLong(cursor.getColumnIndexOrThrow(VisitModel.COLUMN_CUSTOMER_ID)));
                model.setDateTime(cursor.getString(cursor.getColumnIndexOrThrow(VisitModel.COLUMN_DATE_TIME)));
                model.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(VisitModel.COLUMN_LOCATION)));
                model.setWines(cursor.getString(cursor.getColumnIndexOrThrow(VisitModel.COLUMN_WINES)));
                model.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(VisitModel.COLUMN_DESCRIPTION))); // NOVO
                model.setUserId(cursor.getLong(cursor.getColumnIndexOrThrow(VisitModel.COLUMN_USER_ID)));
                cursor.close();
            }
        } finally {
            Close();
        }
        return model;
    }

    public List<VisitModel> getAll() {
        List<VisitModel> list = new ArrayList<>();
        try {
            Open();
            Cursor cursor = db.query(VisitModel.TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    VisitModel model = new VisitModel();
                    model.setVisitId(cursor.getLong(cursor.getColumnIndexOrThrow(VisitModel.COLUMN_ID)));
                    model.setCustomerId(cursor.getLong(cursor.getColumnIndexOrThrow(VisitModel.COLUMN_CUSTOMER_ID)));
                    model.setDateTime(cursor.getString(cursor.getColumnIndexOrThrow(VisitModel.COLUMN_DATE_TIME)));
                    model.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(VisitModel.COLUMN_LOCATION)));
                    model.setWines(cursor.getString(cursor.getColumnIndexOrThrow(VisitModel.COLUMN_WINES)));
                    model.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(VisitModel.COLUMN_DESCRIPTION))); // NOVO
                    model.setUserId(cursor.getLong(cursor.getColumnIndexOrThrow(VisitModel.COLUMN_USER_ID)));
                    list.add(model);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally {
            Close();
        }
        return list;
    }

    public int update(VisitModel model) {
        int rows = 0;
        try {
            Open();
            ContentValues values = new ContentValues();
            values.put(VisitModel.COLUMN_CUSTOMER_ID, model.getCustomerId());
            values.put(VisitModel.COLUMN_DATE_TIME, model.getDateTime());
            values.put(VisitModel.COLUMN_LOCATION, model.getLocation());
            values.put(VisitModel.COLUMN_WINES, model.getWines());
            values.put(VisitModel.COLUMN_DESCRIPTION, model.getDescription()); // NOVO
            values.put(VisitModel.COLUMN_USER_ID, model.getUserId());
            rows = db.update(VisitModel.TABLE_NAME, values,
                    VisitModel.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(model.getVisitId())});
        } finally {
            Close();
        }
        return rows;
    }

    public int delete(long id) {
        int rows = 0;
        try {
            Open();
            rows = db.delete(VisitModel.TABLE_NAME,
                    VisitModel.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(id)});
        } finally {
            Close();
        }
        return rows;
    }
}
