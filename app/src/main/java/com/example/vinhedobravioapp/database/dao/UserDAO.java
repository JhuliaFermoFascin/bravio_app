package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstrataDAO {

    public UserDAO(Context context) {
        helper = new DPOpenHelper(context);
    }

    public long insert(final UserModel userModel) {
        long result = -1;
        try {
            Open();
            ContentValues values = new ContentValues();
            values.put(UserModel.COLUMN_NAME, userModel.getName());
            values.put(UserModel.COLUMN_EMAIL, userModel.getEmail());
            values.put(UserModel.COLUMN_PASSWORD, userModel.getPassword());
            values.put(UserModel.COLUMN_IS_ADMIN, userModel.getIsAdmin());
            values.put(UserModel.COLUMN_STATUS, userModel.getStatus());
            // Se createdAt vier null, gera data atual yyyy-MM-dd
            String createdAt = userModel.getCreatedAt();
            if (createdAt == null) {
                createdAt = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(new java.util.Date());
            }
            values.put(UserModel.COLUMN_CREATED_AT, createdAt);
            values.put(UserModel.COLUMN_LAST_UPDATE, userModel.getLastUpdate());
            values.put(UserModel.COLUMN_LAST_LOGIN, userModel.getLastLogin());
            result = db.insert(UserModel.TABLE_NAME, null, values);
        } finally {
            Close();
        }
        return result;
    }

    public int update(UserModel userModel) {
        int rows = 0;
        try {
            Open();
            ContentValues values = new ContentValues();
            values.put(UserModel.COLUMN_NAME, userModel.getName());
            values.put(UserModel.COLUMN_EMAIL, userModel.getEmail());
            values.put(UserModel.COLUMN_PASSWORD, userModel.getPassword());
            values.put(UserModel.COLUMN_IS_ADMIN, userModel.getIsAdmin());
            values.put(UserModel.COLUMN_STATUS, userModel.getStatus());
            values.put(UserModel.COLUMN_CREATED_AT, userModel.getCreatedAt());
            values.put(UserModel.COLUMN_LAST_UPDATE, userModel.getLastUpdate());
            values.put(UserModel.COLUMN_LAST_LOGIN, userModel.getLastLogin());
            rows = db.update(UserModel.TABLE_NAME, values,
                    UserModel.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(userModel.getUserId())});
        } finally {
            Close();
        }
        return rows;
    }

    public int delete(long userId) {
        int rows = 0;
        try {
            Open();
            rows = db.delete(UserModel.TABLE_NAME,
                    UserModel.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(userId)});
        } finally {
            Close();
        }
        return rows;
    }

    public UserModel getById(long userId) {
        UserModel user = null;
        try {
            Open();
            Cursor cursor = db.query(UserModel.TABLE_NAME, null,
                    UserModel.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(userId)},
                    null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                user = new UserModel();
                user.setUserId(cursor.getLong(cursor.getColumnIndexOrThrow(UserModel.COLUMN_ID)));
                user.setName(cursor.getString(cursor.getColumnIndexOrThrow(UserModel.COLUMN_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(UserModel.COLUMN_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(UserModel.COLUMN_PASSWORD)));
                user.setIsAdmin(cursor.getInt(cursor.getColumnIndexOrThrow(UserModel.COLUMN_IS_ADMIN)));
                user.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow(UserModel.COLUMN_STATUS)));
                user.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(UserModel.COLUMN_CREATED_AT)));
                user.setLastUpdate(cursor.getString(cursor.getColumnIndexOrThrow(UserModel.COLUMN_LAST_UPDATE)));
                user.setLastLogin(cursor.getString(cursor.getColumnIndexOrThrow(UserModel.COLUMN_LAST_LOGIN)));
                cursor.close();
            }
        } finally {
            Close();
        }
        return user;
    }

    public List<UserModel> getAll() {
        List<UserModel> userList = new ArrayList<>();
        try {
            Open();
            Cursor cursor = db.query(UserModel.TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    UserModel user = new UserModel();
                    user.setUserId(cursor.getLong(cursor.getColumnIndexOrThrow(UserModel.COLUMN_ID)));
                    user.setName(cursor.getString(cursor.getColumnIndexOrThrow(UserModel.COLUMN_NAME)));
                    user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(UserModel.COLUMN_EMAIL)));
                    user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(UserModel.COLUMN_PASSWORD)));
                    user.setIsAdmin(cursor.getInt(cursor.getColumnIndexOrThrow(UserModel.COLUMN_IS_ADMIN)));
                    user.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow(UserModel.COLUMN_STATUS)));
                    user.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(UserModel.COLUMN_CREATED_AT)));
                    user.setLastUpdate(cursor.getString(cursor.getColumnIndexOrThrow(UserModel.COLUMN_LAST_UPDATE)));
                    user.setLastLogin(cursor.getString(cursor.getColumnIndexOrThrow(UserModel.COLUMN_LAST_LOGIN)));
                    userList.add(user);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally {
            Close();
        }
        return userList;
    }

    public List<UserModel> getRepresentantes() {
        List<UserModel> representantes = new ArrayList<>();
        try {
            Open();
            Cursor cursor = db.query(
                    UserModel.TABLE_NAME,
                    null,
                    UserModel.COLUMN_IS_ADMIN + " = ?",
                    new String[]{"0"}, // Somente usuários com isAdmin = 0
                    null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    UserModel user = new UserModel();
                    user.setUserId(cursor.getLong(cursor.getColumnIndexOrThrow(UserModel.COLUMN_ID)));
                    user.setName(cursor.getString(cursor.getColumnIndexOrThrow(UserModel.COLUMN_NAME)));
                    user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(UserModel.COLUMN_EMAIL)));
                    user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(UserModel.COLUMN_PASSWORD)));
                    user.setIsAdmin(cursor.getInt(cursor.getColumnIndexOrThrow(UserModel.COLUMN_IS_ADMIN)));
                    user.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow(UserModel.COLUMN_STATUS)));
                    user.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(UserModel.COLUMN_CREATED_AT)));
                    user.setLastUpdate(cursor.getString(cursor.getColumnIndexOrThrow(UserModel.COLUMN_LAST_UPDATE)));
                    user.setLastLogin(cursor.getString(cursor.getColumnIndexOrThrow(UserModel.COLUMN_LAST_LOGIN)));
                    representantes.add(user);
                } while (cursor.moveToNext());

                cursor.close();
            }
        } finally {
            Close();
        }

        return representantes;
    }


    public UserModel findByEmailAndPassword(String email, String password) {
        UserModel user = null;
        try {
            Open();
            Cursor cursor = db.query(UserModel.TABLE_NAME, null,
                    UserModel.COLUMN_EMAIL + " = ? AND " + UserModel.COLUMN_PASSWORD + " = ?",
                    new String[]{email, password},
                    null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                user = new UserModel();
                user.setUserId(cursor.getLong(cursor.getColumnIndexOrThrow(UserModel.COLUMN_ID)));
                user.setName(cursor.getString(cursor.getColumnIndexOrThrow(UserModel.COLUMN_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(UserModel.COLUMN_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(UserModel.COLUMN_PASSWORD)));
                user.setIsAdmin(cursor.getInt(cursor.getColumnIndexOrThrow(UserModel.COLUMN_IS_ADMIN)));
                user.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow(UserModel.COLUMN_STATUS)));
                user.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(UserModel.COLUMN_CREATED_AT)));
                user.setLastUpdate(cursor.getString(cursor.getColumnIndexOrThrow(UserModel.COLUMN_LAST_UPDATE)));
                user.setLastLogin(cursor.getString(cursor.getColumnIndexOrThrow(UserModel.COLUMN_LAST_LOGIN)));
                cursor.close();
            }
        } finally {
            Close();
        }
        return user;
    }
}
