package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;

import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.ClientModel;

public class ClientDAO extends AbstrataDAO {

    public ClientDAO(Context context) {
        helper = new DPOpenHelper(context);
    }

    public long insert(final ClientModel clientModel) {
        long result = -1;
        try {
            Open();
            ContentValues values = new ContentValues();
            values.put(ClientModel.COLUMN_ID, clientModel.getCustomerId());
            values.put(ClientModel.COLUMN_NAME_COMPANY, clientModel.getNameCompanyName());
            values.put(ClientModel.COLUMN_CPF_CNPJ, clientModel.getCpfCnpj());
            values.put(ClientModel.COLUMN_ADDRESS, clientModel.getAddress());
            values.put(ClientModel.COLUMN_REGION, clientModel.getRegion());
            values.put(ClientModel.COLUMN_PHONE, clientModel.getPhone());
            values.put(ClientModel.COLUMN_EMAIL, clientModel.getEmail());
            result = db.insert(ClientModel.TABLE_NAME, null, values);
        } finally {
            Close();
        }
        return result;
    }
}
