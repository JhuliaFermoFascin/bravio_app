package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;

import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.OrderItemModel;

public class OrderItemDAO extends AbstrataDAO {

    public OrderItemDAO(Context context) {
        helper = new DPOpenHelper(context);
    }

    public long insert(final OrderItemModel orderItemModel) {
        long result = -1;
        try {
            Open();
            ContentValues values = new ContentValues();
            values.put(OrderItemModel.COLUMN_ID, orderItemModel.getOrderItemId());
            values.put(OrderItemModel.COLUMN_WINE_ID, orderItemModel.getWineId());
            values.put(OrderItemModel.COLUMN_VALUE, orderItemModel.getValue());
            values.put(OrderItemModel.COLUMN_QUANTITY, orderItemModel.getQuantity());
            values.put(OrderItemModel.COLUMN_ORDER_ID, orderItemModel.getOrderId());
            result = db.insert(OrderItemModel.TABLE_NAME, null, values);
        } finally {
            Close();
        }
        return result;
    }
}
