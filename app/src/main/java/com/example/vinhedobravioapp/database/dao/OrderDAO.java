package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.InventoryMovementModel;
import com.example.vinhedobravioapp.database.model.OrderItemModel;
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
            values.put(OrderModel.COLUMN_TOTAL, orderModel.getTotal());
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
                    order.setTotal(cursor.getDouble(cursor.getColumnIndexOrThrow(OrderModel.COLUMN_TOTAL)));
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
                order.setTotal(cursor.getDouble(cursor.getColumnIndexOrThrow(OrderModel.COLUMN_TOTAL)));
                order.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(OrderModel.COLUMN_STATUS)));
                order.setUserId(cursor.getLong(cursor.getColumnIndexOrThrow(OrderModel.COLUMN_USER_ID)));
                cursor.close();
            }
        } finally {
            Close();
        }
        return order;
    }

    public long inserirPedidoCompleto(OrderModel cabecalho,
                                      List<OrderItemModel> itens,
                                      long usuarioId,
                                      Context ctx) {

        SQLiteDatabase db = helper.getWritableDatabase();
        long pedidoId;

        OrderItemDAO itemDAO         = new OrderItemDAO(ctx);
        InventoryMovementDAO movDAO  = new InventoryMovementDAO(ctx);

        try {
            db.beginTransaction();

            double totalPedido = 0.0;
            for (OrderItemModel it : itens) {
                totalPedido += it.getValue() * it.getQuantity();
            }

            ContentValues cv = new ContentValues();
            cv.put(OrderModel.COLUMN_CUSTOMER_ID, cabecalho.getCustomerId());
            cv.put(OrderModel.COLUMN_DATE,        cabecalho.getDate());
            cv.put(OrderModel.COLUMN_STATUS,      "ABERTO");
            cv.put(OrderModel.COLUMN_USER_ID,     usuarioId);
            cv.put(OrderModel.COLUMN_TOTAL,       totalPedido);
            pedidoId = db.insertOrThrow(OrderModel.TABLE_NAME, null, cv);

            for (OrderItemModel it : itens) {
                it.setOrderId(pedidoId);
                itemDAO.insert(db, it);

                InventoryMovementModel mov = new InventoryMovementModel();
                mov.setWineId(it.getWineId());
                mov.setMovementType("SAIDA");
                mov.setQuantity(it.getQuantity());
                mov.setUnitPrice(it.getValue());
                mov.setDocumentReference("PEDIDO #" + pedidoId);
                mov.setUserId(usuarioId);
                movDAO.insert(mov);
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
//            db.close();
        }

        return pedidoId;
    }

    public void atualizarStatus(long orderId, String novoStatus) {
        try {
            Open();
            ContentValues values = new ContentValues();
            values.put(OrderModel.COLUMN_STATUS, novoStatus);
            db.update(OrderModel.TABLE_NAME, values,
                    OrderModel.COLUMN_ID + " = ?", new String[]{String.valueOf(orderId)});
        } catch (Exception e) {
            Log.e("OrderDAO", "Erro ao atualizar status", e);
        } finally {
            Close();
        }
    }



    public int update(OrderModel orderModel) {
        int rows = 0;
        try {
            Open();
            ContentValues values = new ContentValues();
            values.put(OrderModel.COLUMN_CUSTOMER_ID, orderModel.getCustomerId());
            values.put(OrderModel.COLUMN_DATE, orderModel.getDate());
            values.put(OrderModel.COLUMN_TOTAL, orderModel.getTotal());
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
