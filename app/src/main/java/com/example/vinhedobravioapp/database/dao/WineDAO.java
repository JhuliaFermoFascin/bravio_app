package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;

import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.WineModel;

public class WineDAO extends AbstrataDAO{

    public WineDAO(Context context){
        helper = new DPOpenHelper(context);
    }

    public long insert(final WineModel wineModel){

        long result = -1;

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(WineModel.WINE_ID_COLUMN, wineModel.getId());
            values.put(WineModel.NAME_COLUMN, wineModel.getName());
            values.put(WineModel.TYPE_COLUMN, wineModel.getType());
            values.put(WineModel.VINTAGE_COLUMN, wineModel.getVintage());
            values.put(WineModel.DESCRIPTION_COLUM, wineModel.getDescription());
            values.put(WineModel.TASTING_NOTE_COLUMN, wineModel.getTasting_note());
            values.put(WineModel.FOOD_PARINGS_COLUMN, wineModel.getFood_parings());
            values.put(WineModel.ALCOHOL_CONTENT_COLUMN, wineModel.getAlcohol_content());
            values.put(WineModel.VOLUME_COLUMN, wineModel.getVolume());

            result = db.insert(WineModel.TABLE_NAME, null, values);
        }
        finally {
            Close();
        }
        return result;
    }
}