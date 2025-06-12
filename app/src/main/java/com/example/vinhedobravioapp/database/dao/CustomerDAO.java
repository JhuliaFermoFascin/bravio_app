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
            values.put(CustomerModel.COLUMN_ID, customer.getCustomerId());
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
}
