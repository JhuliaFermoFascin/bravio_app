package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;

import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.CustomerModel;

public class CustomerDAO extends AbstrataDAO {

    public CustomerDAO(Context context) {
        helper = new DPOpenHelper(context);
    }

    public long insert(final CustomerModel customer) {
        long result = -1;
        try {
            Open();
            ContentValues values = new ContentValues();
            values.put(CustomerModel.COLUMN_NAME_COMPANY, customer.getNameCompanyName());
            values.put(CustomerModel.COLUMN_CPF_CNPJ, customer.getCpfCnpj());
            values.put(CustomerModel.COLUMN_ADDRESS, customer.getAddress());
            values.put(CustomerModel.COLUMN_REGION, customer.getRegion());
            values.put(CustomerModel.COLUMN_PHONE, customer.getPhone());
            values.put(CustomerModel.COLUMN_EMAIL, customer.getEmail());
            result = db.insert(CustomerModel.TABLE_NAME, null, values);
        } finally {
            Close();
        }
        return result;
    }

    public CustomerModel getById(long id) {
        CustomerModel model = null;
        try {
            Open();
            android.database.Cursor cursor = db.query(CustomerModel.TABLE_NAME, null,
                CustomerModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                model = new CustomerModel();
                model.setCustomerId(cursor.getLong(cursor.getColumnIndexOrThrow(CustomerModel.COLUMN_ID)));
                model.setNameCompanyName(cursor.getString(cursor.getColumnIndexOrThrow(CustomerModel.COLUMN_NAME_COMPANY)));
                model.setCpfCnpj(cursor.getString(cursor.getColumnIndexOrThrow(CustomerModel.COLUMN_CPF_CNPJ)));
                model.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(CustomerModel.COLUMN_ADDRESS)));
                model.setRegion(cursor.getString(cursor.getColumnIndexOrThrow(CustomerModel.COLUMN_REGION)));
                model.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(CustomerModel.COLUMN_PHONE)));
                model.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(CustomerModel.COLUMN_EMAIL)));
                cursor.close();
            }
        } finally { Close(); }
        return model;
    }

    public java.util.List<CustomerModel> getAll() {
        java.util.List<CustomerModel> list = new java.util.ArrayList<>();
        try { Open();
            android.database.Cursor cursor = db.query(CustomerModel.TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    CustomerModel model = new CustomerModel();
                    model.setCustomerId(cursor.getLong(cursor.getColumnIndexOrThrow(CustomerModel.COLUMN_ID)));
                    model.setNameCompanyName(cursor.getString(cursor.getColumnIndexOrThrow(CustomerModel.COLUMN_NAME_COMPANY)));
                    model.setCpfCnpj(cursor.getString(cursor.getColumnIndexOrThrow(CustomerModel.COLUMN_CPF_CNPJ)));
                    model.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(CustomerModel.COLUMN_ADDRESS)));
                    model.setRegion(cursor.getString(cursor.getColumnIndexOrThrow(CustomerModel.COLUMN_REGION)));
                    model.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(CustomerModel.COLUMN_PHONE)));
                    model.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(CustomerModel.COLUMN_EMAIL)));
                    list.add(model);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally { Close(); }
        return list;
    }

    public int update(CustomerModel model) {
        int rows = 0;
        try { Open();
            android.content.ContentValues values = new android.content.ContentValues();
            values.put(CustomerModel.COLUMN_NAME_COMPANY, model.getNameCompanyName());
            values.put(CustomerModel.COLUMN_CPF_CNPJ, model.getCpfCnpj());
            values.put(CustomerModel.COLUMN_ADDRESS, model.getAddress());
            values.put(CustomerModel.COLUMN_REGION, model.getRegion());
            values.put(CustomerModel.COLUMN_PHONE, model.getPhone());
            values.put(CustomerModel.COLUMN_EMAIL, model.getEmail());
            rows = db.update(CustomerModel.TABLE_NAME, values,
                CustomerModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(model.getCustomerId())});
        } finally { Close(); }
        return rows;
    }

    public int delete(long id) {
        int rows = 0;
        try { Open();
            rows = db.delete(CustomerModel.TABLE_NAME,
                CustomerModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        } finally { Close(); }
        return rows;
    }
}
