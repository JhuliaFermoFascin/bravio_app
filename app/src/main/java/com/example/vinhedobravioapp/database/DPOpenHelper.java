package com.example.vinhedobravioapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.vinhedobravioapp.database.model.UserModel;
import com.example.vinhedobravioapp.database.model.ClientModel;
import com.example.vinhedobravioapp.database.model.WineTypeModel;
import com.example.vinhedobravioapp.database.model.WineModel;
import com.example.vinhedobravioapp.database.model.TastingNoteModel;
import com.example.vinhedobravioapp.database.model.OrderModel;
import com.example.vinhedobravioapp.database.model.OrderItemModel;
import com.example.vinhedobravioapp.database.model.InventoryMovementModel;
import com.example.vinhedobravioapp.database.model.VisitModel;

public class DPOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Vinhedo Bravio";
    private static final int DATABASE_VERSION = 1;

    public DPOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(UserModel.CREATE_TABLE_USER);
        sqLiteDatabase.execSQL(ClientModel.CREATE_TABLE_CUSTOMER);
        sqLiteDatabase.execSQL(WineTypeModel.CREATE_TABLE_WINE_TYPE);
        sqLiteDatabase.execSQL(WineModel.CREATE_TABLE_VINHO);
        sqLiteDatabase.execSQL(TastingNoteModel.CREATE_TABLE_TASTING_NOTE);
        sqLiteDatabase.execSQL(OrderModel.CREATE_TABLE);
        sqLiteDatabase.execSQL(OrderItemModel.CREATE_TABLE);
        sqLiteDatabase.execSQL(InventoryMovementModel.CREATE_TABLE);
        sqLiteDatabase.execSQL(VisitModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
