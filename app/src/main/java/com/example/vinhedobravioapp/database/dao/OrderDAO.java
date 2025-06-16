package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends AbstrataDAO {

    public OrderDAO(Context context) {
        helper = new DPOpenHelper(context);
    }

    public long insert(final OrderModel orderModel) {
        long result = -1;
        try {
            Open();
            ContentValues values = new ContentValues();
            values.put(OrderModel.COLUMN_CUSTOMER_ID, orderModel.getCustomerId());
            values.put(OrderModel.COLUMN_DATE, orderModel.getDate());
            values.put(OrderModel.COLUMN_STATUS, orderModel.getStatus());
            values.put(OrderModel.COLUMN_USER_ID, orderModel.getUserId());
            result = db.insert(OrderModel.TABLE_NAME, null, values);
        } finally {
            Close();
        }
        return result;
    }
    public List<OrderModel> getAll() {
        List<OrderModel> orderList = new ArrayList<>();
        Cursor cursor = null;

        try {
            Open();
            cursor = db.query(OrderModel.TABLE_NAME, null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    OrderModel order = new OrderModel();
                    order.setOrderId(cursor.getLong(cursor.getColumnIndexOrThrow(OrderModel.COLUMN_ID)));
                    order.setCustomerId(cursor.getLong(cursor.getColumnIndexOrThrow(OrderModel.COLUMN_CUSTOMER_ID)));
                    order.setDate(cursor.getString(cursor.getColumnIndexOrThrow(OrderModel.COLUMN_DATE)));
                    order.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(OrderModel.COLUMN_STATUS)));
                    order.setUserId(cursor.getLong(cursor.getColumnIndexOrThrow(OrderModel.COLUMN_USER_ID)));

                    orderList.add(order);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.e("OrderDAO", "Erro ao buscar pedidos", e);
        } finally {
            if (cursor != null) cursor.close();
            Close();
        }

        return orderList;
    }

    public OrderModel getById(long id) {
        OrderModel order = null;
        try {
            Open();
            Cursor cursor = db.query(OrderModel.TABLE_NAME, null,
                    OrderModel.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(id)},
                    null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                order = new OrderModel();
                order.setOrderId(cursor.getLong(cursor.getColumnIndexOrThrow(OrderModel.COLUMN_ID)));
                order.setCustomerId(cursor.getLong(cursor.getColumnIndexOrThrow(OrderModel.COLUMN_CUSTOMER_ID)));
                order.setDate(cursor.getString(cursor.getColumnIndexOrThrow(OrderModel.COLUMN_DATE)));
                order.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(OrderModel.COLUMN_STATUS)));
                order.setUserId(cursor.getLong(cursor.getColumnIndexOrThrow(OrderModel.COLUMN_USER_ID)));
                cursor.close();
            }
        } finally {
            Close();
        }
        return order;
    }

    public int update(OrderModel orderModel) {
        int rows = 0;
        try {
            Open();
            ContentValues values = new ContentValues();
            values.put(OrderModel.COLUMN_CUSTOMER_ID, orderModel.getCustomerId());
            values.put(OrderModel.COLUMN_DATE, orderModel.getDate());
            values.put(OrderModel.COLUMN_STATUS, orderModel.getStatus());
            values.put(OrderModel.COLUMN_USER_ID, orderModel.getUserId());
            rows = db.update(OrderModel.TABLE_NAME, values,
                    OrderModel.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(orderModel.getOrderId())});
        } finally {
            Close();
        }
        return rows;
    }

    public int delete(long id) {
        int rows = 0;
        try {
            Open();
            rows = db.delete(OrderModel.TABLE_NAME,
                    OrderModel.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(id)});
        } finally {
            Close();
        }
        return rows;
    }

}
