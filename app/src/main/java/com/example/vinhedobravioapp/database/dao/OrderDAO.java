package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;

import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.OrderModel;

public class OrderDAO extends AbstrataDAO {

    public OrderDAO(Context context) {
        helper = new DPOpenHelper(context);
    }

    public long insert(final OrderModel orderModel) {
        long result = -1;
        try {
            Open();
            ContentValues values = new ContentValues();
            values.put(OrderModel.COLUMN_ID, orderModel.getOrderId());
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
}
