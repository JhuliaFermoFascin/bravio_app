package com.example.vinhedobravioapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.vinhedobravioapp.database.model.VinhoModel;

public class DPOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Vinhedo Bravio";
    private static final int DATABASE_VERSION = 1;

    public DPOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(VinhoModel.CREATE_TABLE_VINHO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
