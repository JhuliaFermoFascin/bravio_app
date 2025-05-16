package com.example.vinhedobravioapp.database.dao;

import android.database.sqlite.SQLiteDatabase;

import com.example.vinhedobravioapp.database.DPOpenHelper;

public abstract class AbstrataDAO {
    protected SQLiteDatabase db;
    protected DPOpenHelper helper;

    protected final void Open(){
        db = helper.getWritableDatabase();
    }
    protected final void Close(){
        helper.close();
    }

}
