package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;

import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.InventoryMovementModel;

public class InventoryMovementDAO extends AbstrataDAO {

    public InventoryMovementDAO(Context context) {
        helper = new DPOpenHelper(context);
    }

    public long insert(final InventoryMovementModel model) {
        long result = -1;
        try {
            Open();
            ContentValues values = new ContentValues();
            values.put(InventoryMovementModel.COLUMN_ID, model.getMovementId());
            values.put(InventoryMovementModel.COLUMN_WINE_ID, model.getWineId());
            values.put(InventoryMovementModel.COLUMN_MOVEMENT_TYPE, model.getMovementType());
            values.put(InventoryMovementModel.COLUMN_QUANTITY, model.getQuantity());
            values.put(InventoryMovementModel.COLUMN_UNIT_PRICE, model.getUnitPrice());
            values.put(InventoryMovementModel.COLUMN_MOVEMENT_DATE, model.getMovementDate());
            values.put(InventoryMovementModel.COLUMN_DOCUMENT_REFERENCE, model.getDocumentReference());
            values.put(InventoryMovementModel.COLUMN_USER_ID, model.getUserId());
            values.put(InventoryMovementModel.COLUMN_NOTES, model.getNotes());
            result = db.insert(InventoryMovementModel.TABLE_NAME, null, values);
        } finally {
            Close();
        }
        return result;
    }

    public InventoryMovementModel getById(long id) {
        InventoryMovementModel model = null;
        try {
            Open();
            android.database.Cursor cursor = db.query(InventoryMovementModel.TABLE_NAME, null,
                InventoryMovementModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                model = new InventoryMovementModel();
                model.setMovementId(cursor.getLong(cursor.getColumnIndexOrThrow(InventoryMovementModel.COLUMN_ID)));
                model.setWineId(cursor.getLong(cursor.getColumnIndexOrThrow(InventoryMovementModel.COLUMN_WINE_ID)));
                model.setMovementType(cursor.getString(cursor.getColumnIndexOrThrow(InventoryMovementModel.COLUMN_MOVEMENT_TYPE)));
                model.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(InventoryMovementModel.COLUMN_QUANTITY)));
                model.setUnitPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(InventoryMovementModel.COLUMN_UNIT_PRICE)));
                model.setMovementDate(cursor.getString(cursor.getColumnIndexOrThrow(InventoryMovementModel.COLUMN_MOVEMENT_DATE)));
                model.setDocumentReference(cursor.getString(cursor.getColumnIndexOrThrow(InventoryMovementModel.COLUMN_DOCUMENT_REFERENCE)));
                model.setUserId(cursor.getLong(cursor.getColumnIndexOrThrow(InventoryMovementModel.COLUMN_USER_ID)));
                model.setNotes(cursor.getString(cursor.getColumnIndexOrThrow(InventoryMovementModel.COLUMN_NOTES)));
                cursor.close();
            }
        } finally { Close(); }
        return model;
    }

    public java.util.List<InventoryMovementModel> getAll() {
        java.util.List<InventoryMovementModel> list = new java.util.ArrayList<>();
        try { Open();
            android.database.Cursor cursor = db.query(InventoryMovementModel.TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    InventoryMovementModel model = new InventoryMovementModel();
                    model.setMovementId(cursor.getLong(cursor.getColumnIndexOrThrow(InventoryMovementModel.COLUMN_ID)));
                    model.setWineId(cursor.getLong(cursor.getColumnIndexOrThrow(InventoryMovementModel.COLUMN_WINE_ID)));
                    model.setMovementType(cursor.getString(cursor.getColumnIndexOrThrow(InventoryMovementModel.COLUMN_MOVEMENT_TYPE)));
                    model.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(InventoryMovementModel.COLUMN_QUANTITY)));
                    model.setUnitPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(InventoryMovementModel.COLUMN_UNIT_PRICE)));
                    model.setMovementDate(cursor.getString(cursor.getColumnIndexOrThrow(InventoryMovementModel.COLUMN_MOVEMENT_DATE)));
                    model.setDocumentReference(cursor.getString(cursor.getColumnIndexOrThrow(InventoryMovementModel.COLUMN_DOCUMENT_REFERENCE)));
                    model.setUserId(cursor.getLong(cursor.getColumnIndexOrThrow(InventoryMovementModel.COLUMN_USER_ID)));
                    model.setNotes(cursor.getString(cursor.getColumnIndexOrThrow(InventoryMovementModel.COLUMN_NOTES)));
                    list.add(model);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally { Close(); }
        return list;
    }

    public int update(InventoryMovementModel model) {
        int rows = 0;
        try { Open();
            android.content.ContentValues values = new android.content.ContentValues();
            values.put(InventoryMovementModel.COLUMN_WINE_ID, model.getWineId());
            values.put(InventoryMovementModel.COLUMN_MOVEMENT_TYPE, model.getMovementType());
            values.put(InventoryMovementModel.COLUMN_QUANTITY, model.getQuantity());
            values.put(InventoryMovementModel.COLUMN_UNIT_PRICE, model.getUnitPrice());
            values.put(InventoryMovementModel.COLUMN_MOVEMENT_DATE, model.getMovementDate());
            values.put(InventoryMovementModel.COLUMN_DOCUMENT_REFERENCE, model.getDocumentReference());
            values.put(InventoryMovementModel.COLUMN_USER_ID, model.getUserId());
            values.put(InventoryMovementModel.COLUMN_NOTES, model.getNotes());
            rows = db.update(InventoryMovementModel.TABLE_NAME, values,
                InventoryMovementModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(model.getMovementId())});
        } finally { Close(); }
        return rows;
    }

    public int delete(long id) {
        int rows = 0;
        try { Open();
            rows = db.delete(InventoryMovementModel.TABLE_NAME,
                InventoryMovementModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        } finally { Close(); }
        return rows;
    }

    public int getCurrentQuantityByWineId(long wineId) {
        int total = 0;
        try {
            Open();
            android.database.Cursor cursor = db.query(InventoryMovementModel.TABLE_NAME, null,
                InventoryMovementModel.COLUMN_WINE_ID + " = ?",
                new String[]{String.valueOf(wineId)},
                null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String type = cursor.getString(cursor.getColumnIndexOrThrow(InventoryMovementModel.COLUMN_MOVEMENT_TYPE));
                    int qty = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryMovementModel.COLUMN_QUANTITY));
                    if ("ENTRADA".equalsIgnoreCase(type)) {
                        total += qty;
                    } else if ("SAIDA".equalsIgnoreCase(type)) {
                        total -= qty;
                    }
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally { Close(); }
        return total;
    }
}
