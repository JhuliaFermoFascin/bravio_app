package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;

import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.UserModel;

public class UserDAO extends AbstrataDAO {

    public UserDAO(Context context) {
        helper = new DPOpenHelper(context);
    }

    public long insert(final UserModel userModel) {
        long result = -1;
        try {
            Open();
            ContentValues values = new ContentValues();
            values.put(UserModel.COLUMN_ID, userModel.getUserId());
            values.put(UserModel.COLUMN_NAME, userModel.getName());
            values.put(UserModel.COLUMN_EMAIL, userModel.getEmail());
            values.put(UserModel.COLUMN_PASSWORD, userModel.getPassword());
            values.put(UserModel.COLUMN_IS_ADMIN, userModel.getIsAdmin());
            values.put(UserModel.COLUMN_STATUS, userModel.getStatus());
            values.put(UserModel.COLUMN_CREATED_AT, userModel.getCreatedAt());
            values.put(UserModel.COLUMN_LAST_UPDATE, userModel.getLastUpdate());
            values.put(UserModel.COLUMN_LAST_LOGIN, userModel.getLastLogin());
            result = db.insert(UserModel.TABLE_NAME, null, values);
        } finally {
            Close();
        }
        return result;
    }
}
