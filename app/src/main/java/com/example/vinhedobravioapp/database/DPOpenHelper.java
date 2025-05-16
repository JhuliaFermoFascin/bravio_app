package com.example.vinhedobravioapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.vinhedobravioapp.database.model.WineModel;

public class DPOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Vinhedo Bravio";
    private static final int DATABASE_VERSION = 1;

    public DPOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(WineModel.CREATE_WINE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
