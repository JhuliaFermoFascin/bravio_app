package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.OrderItemModel;

import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO extends AbstrataDAO {

    public OrderItemDAO(Context context) {
        helper = new DPOpenHelper(context);
    }

    public long insert(SQLiteDatabase db, OrderItemModel item) {
        ContentValues v = new ContentValues();
        v.put(OrderItemModel.COLUMN_WINE_ID,  item.getWineId());
        v.put(OrderItemModel.COLUMN_VALUE,    item.getValue());
        v.put(OrderItemModel.COLUMN_QUANTITY, item.getQuantity());
        v.put(OrderItemModel.COLUMN_ORDER_ID, item.getOrderId());
        return db.insertOrThrow(OrderItemModel.TABLE_NAME, null, v);
    }

    public OrderItemModel getById(long id) {
        OrderItemModel model = null;
        try {
            Open();
            android.database.Cursor cursor = db.query(OrderItemModel.TABLE_NAME, null,
                OrderItemModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                model = new OrderItemModel();
                model.setOrderItemId(cursor.getLong(cursor.getColumnIndexOrThrow(OrderItemModel.COLUMN_ID)));
                model.setWineId(cursor.getLong(cursor.getColumnIndexOrThrow(OrderItemModel.COLUMN_WINE_ID)));
                model.setValue(cursor.getDouble(cursor.getColumnIndexOrThrow(OrderItemModel.COLUMN_VALUE)));
                model.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(OrderItemModel.COLUMN_QUANTITY)));
                model.setOrderId(cursor.getLong(cursor.getColumnIndexOrThrow(OrderItemModel.COLUMN_ORDER_ID)));
                cursor.close();
            }
        } finally {
            Close();
        }
        return model;
    }

    public List<OrderItemModel> getByOrderId(long orderId) {
        List<OrderItemModel> list = new ArrayList<>();
        try {
            Open();
            android.database.Cursor cursor = db.query(OrderItemModel.TABLE_NAME, null,
                    OrderItemModel.COLUMN_ORDER_ID + " = ?",
                    new String[]{String.valueOf(orderId)},
                    null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    OrderItemModel model = new OrderItemModel();
                    model.setOrderItemId(cursor.getLong(cursor.getColumnIndexOrThrow(OrderItemModel.COLUMN_ID)));
                    model.setWineId(cursor.getLong(cursor.getColumnIndexOrThrow(OrderItemModel.COLUMN_WINE_ID)));
                    model.setValue(cursor.getDouble(cursor.getColumnIndexOrThrow(OrderItemModel.COLUMN_VALUE)));
                    model.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(OrderItemModel.COLUMN_QUANTITY)));
                    model.setOrderId(cursor.getLong(cursor.getColumnIndexOrThrow(OrderItemModel.COLUMN_ORDER_ID)));
                    list.add(model);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally {
            Close();
        }
        return list;
    }


    public java.util.List<OrderItemModel> getAll() {
        java.util.List<OrderItemModel> list = new java.util.ArrayList<>();
        try {
            Open();
            android.database.Cursor cursor = db.query(OrderItemModel.TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    OrderItemModel model = new OrderItemModel();
                    model.setOrderItemId(cursor.getLong(cursor.getColumnIndexOrThrow(OrderItemModel.COLUMN_ID)));
                    model.setWineId(cursor.getLong(cursor.getColumnIndexOrThrow(OrderItemModel.COLUMN_WINE_ID)));
                    model.setValue(cursor.getDouble(cursor.getColumnIndexOrThrow(OrderItemModel.COLUMN_VALUE)));
                    model.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(OrderItemModel.COLUMN_QUANTITY)));
                    model.setOrderId(cursor.getLong(cursor.getColumnIndexOrThrow(OrderItemModel.COLUMN_ORDER_ID)));
                    list.add(model);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally {
            Close();
        }
        return list;
    }

    public int update(OrderItemModel model) {
        int rows = 0;
        try {
            Open();
            android.content.ContentValues values = new android.content.ContentValues();
            values.put(OrderItemModel.COLUMN_WINE_ID, model.getWineId());
            values.put(OrderItemModel.COLUMN_VALUE, model.getValue());
            values.put(OrderItemModel.COLUMN_QUANTITY, model.getQuantity());
            values.put(OrderItemModel.COLUMN_ORDER_ID, model.getOrderId());
            rows = db.update(OrderItemModel.TABLE_NAME, values,
                OrderItemModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(model.getOrderItemId())});
        } finally {
            Close();
        }
        return rows;
    }

    public int delete(long id) {
        int rows = 0;
        try {
            Open();
            rows = db.delete(OrderItemModel.TABLE_NAME,
                OrderItemModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        } finally {
            Close();
        }
        return rows;
    }
}
