package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.WineModel;

import java.util.ArrayList;
import java.util.List;

public class WineDAO extends AbstrataDAO {

    public WineDAO(Context context) {
        helper = new DPOpenHelper(context);
    }

    // CREATE
    public long insert(final WineModel wineModel) {
        long result = -1;
        try {
            Open();
            ContentValues values = new ContentValues();
            values.put(WineModel.NAME_COLUMN, wineModel.getName());
            values.put(WineModel.WINERY_ID_COLUMN, wineModel.getWineryId());
            values.put(WineModel.WINE_TYPE_ID_COLUMN, wineModel.getWineTypeId());
            values.put(WineModel.COMMERCIAL_CATEGORY_ID_COLUMN, wineModel.getCommercialCategoryId());
            values.put(WineModel.ORIGIN_ID_COLUMN, wineModel.getOriginId());
            values.put(WineModel.VINTAGE_COLUMN, wineModel.getVintage());
            values.put(WineModel.DESCRIPTION_COLUMN, wineModel.getDescription());
            values.put(WineModel.COMPOSITION_TYPE_COLUMN, wineModel.getCompositionType());
            values.put(WineModel.TASTING_NOTE_ID_COLUMN, wineModel.getTastingNoteId());
            values.put(WineModel.FOOD_PAIRINGS_COLUMN, wineModel.getFoodPairings());
            values.put(WineModel.ALCOHOL_CONTENT_COLUMN, wineModel.getAlcoholContent());
            values.put(WineModel.VOLUME_COLUMN, wineModel.getVolume());
            values.put(WineModel.GRAPE_ID_COLUMN, wineModel.getGrapeId());
            values.put(WineModel.ACIDITY_COLUMN, wineModel.getAcidity());
            values.put(WineModel.IDEAL_TEMPERATURE_COLUMN, wineModel.getIdealTemperatureCelsius());
            values.put(WineModel.AGING_POTENTIAL_COLUMN, wineModel.getAgingPotential());
            values.put(WineModel.UNIT_PRICE_COLUMN, wineModel.getUnitPrice());
            result = db.insert(WineModel.TABLE_NAME, null, values);
        } finally {
            Close();
        }
        return result;
    }

    // READ ALL
    public List<WineModel> getAll() {
        List<WineModel> wineList = new ArrayList<>();
        try {
            Open();
            Cursor cursor = db.query(WineModel.TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    WineModel wine = new WineModel();
                    wine.setWineId(cursor.getLong(cursor.getColumnIndexOrThrow(WineModel.WINE_ID_COLUMN)));
                    wine.setName(cursor.getString(cursor.getColumnIndexOrThrow(WineModel.NAME_COLUMN)));
                    wine.setWineryId(cursor.getLong(cursor.getColumnIndexOrThrow(WineModel.WINERY_ID_COLUMN)));
                    wine.setWineTypeId(cursor.getLong(cursor.getColumnIndexOrThrow(WineModel.WINE_TYPE_ID_COLUMN)));
                    wine.setCommercialCategoryId(cursor.getLong(cursor.getColumnIndexOrThrow(WineModel.COMMERCIAL_CATEGORY_ID_COLUMN)));
                    wine.setOriginId(cursor.getLong(cursor.getColumnIndexOrThrow(WineModel.ORIGIN_ID_COLUMN)));
                    wine.setVintage(cursor.getString(cursor.getColumnIndexOrThrow(WineModel.VINTAGE_COLUMN)));
                    wine.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(WineModel.DESCRIPTION_COLUMN)));
                    wine.setCompositionType(cursor.getString(cursor.getColumnIndexOrThrow(WineModel.COMPOSITION_TYPE_COLUMN)));
                    wine.setTastingNoteId(cursor.getLong(cursor.getColumnIndexOrThrow(WineModel.TASTING_NOTE_ID_COLUMN)));
                    wine.setFoodPairings(cursor.getString(cursor.getColumnIndexOrThrow(WineModel.FOOD_PAIRINGS_COLUMN)));
                    wine.setAlcoholContent(cursor.getDouble(cursor.getColumnIndexOrThrow(WineModel.ALCOHOL_CONTENT_COLUMN)));
                    wine.setVolume(cursor.getInt(cursor.getColumnIndexOrThrow(WineModel.VOLUME_COLUMN)));
                    wine.setGrapeId(cursor.getLong(cursor.getColumnIndexOrThrow(WineModel.GRAPE_ID_COLUMN)));
                    wine.setAcidity(cursor.getString(cursor.getColumnIndexOrThrow(WineModel.ACIDITY_COLUMN)));
                    wine.setIdealTemperatureCelsius(cursor.getDouble(cursor.getColumnIndexOrThrow(WineModel.IDEAL_TEMPERATURE_COLUMN)));
                    wine.setAgingPotential(cursor.getString(cursor.getColumnIndexOrThrow(WineModel.AGING_POTENTIAL_COLUMN)));
                    wine.setUnitPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(WineModel.UNIT_PRICE_COLUMN)));
                    wineList.add(wine);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally {
            Close();
        }
        return wineList;
    }

    // READ BY ID
    public WineModel getById(long id) {
        WineModel wine = null;
        try {
            Open();
            Cursor cursor = db.query(WineModel.TABLE_NAME, null,
                    WineModel.WINE_ID_COLUMN + " = ?",
                    new String[]{String.valueOf(id)},
                    null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                wine = new WineModel();
                wine.setWineId(cursor.getLong(cursor.getColumnIndexOrThrow(WineModel.WINE_ID_COLUMN)));
                wine.setName(cursor.getString(cursor.getColumnIndexOrThrow(WineModel.NAME_COLUMN)));
                wine.setWineryId(cursor.getLong(cursor.getColumnIndexOrThrow(WineModel.WINERY_ID_COLUMN)));
                wine.setWineTypeId(cursor.getLong(cursor.getColumnIndexOrThrow(WineModel.WINE_TYPE_ID_COLUMN)));
                wine.setCommercialCategoryId(cursor.getLong(cursor.getColumnIndexOrThrow(WineModel.COMMERCIAL_CATEGORY_ID_COLUMN)));
                wine.setOriginId(cursor.getLong(cursor.getColumnIndexOrThrow(WineModel.ORIGIN_ID_COLUMN)));
                wine.setVintage(cursor.getString(cursor.getColumnIndexOrThrow(WineModel.VINTAGE_COLUMN)));
                wine.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(WineModel.DESCRIPTION_COLUMN)));
                wine.setCompositionType(cursor.getString(cursor.getColumnIndexOrThrow(WineModel.COMPOSITION_TYPE_COLUMN)));
                wine.setTastingNoteId(cursor.getLong(cursor.getColumnIndexOrThrow(WineModel.TASTING_NOTE_ID_COLUMN)));
                wine.setFoodPairings(cursor.getString(cursor.getColumnIndexOrThrow(WineModel.FOOD_PAIRINGS_COLUMN)));
                wine.setAlcoholContent(cursor.getDouble(cursor.getColumnIndexOrThrow(WineModel.ALCOHOL_CONTENT_COLUMN)));
                wine.setVolume(cursor.getInt(cursor.getColumnIndexOrThrow(WineModel.VOLUME_COLUMN)));
                wine.setGrapeId(cursor.getLong(cursor.getColumnIndexOrThrow(WineModel.GRAPE_ID_COLUMN)));
                wine.setAcidity(cursor.getString(cursor.getColumnIndexOrThrow(WineModel.ACIDITY_COLUMN)));
                wine.setIdealTemperatureCelsius(cursor.getDouble(cursor.getColumnIndexOrThrow(WineModel.IDEAL_TEMPERATURE_COLUMN)));
                wine.setAgingPotential(cursor.getString(cursor.getColumnIndexOrThrow(WineModel.AGING_POTENTIAL_COLUMN)));
                wine.setUnitPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(WineModel.UNIT_PRICE_COLUMN)));
                cursor.close();
            }
        } finally {
            Close();
        }
        return wine;
    }

    // UPDATE
    public int update(WineModel wineModel) {
        int rows = 0;
        try {
            Open();
            ContentValues values = new ContentValues();
            values.put(WineModel.NAME_COLUMN, wineModel.getName());
            values.put(WineModel.WINERY_ID_COLUMN, wineModel.getWineryId());
            values.put(WineModel.WINE_TYPE_ID_COLUMN, wineModel.getWineTypeId());
            values.put(WineModel.COMMERCIAL_CATEGORY_ID_COLUMN, wineModel.getCommercialCategoryId());
            values.put(WineModel.ORIGIN_ID_COLUMN, wineModel.getOriginId());
            values.put(WineModel.VINTAGE_COLUMN, wineModel.getVintage());
            values.put(WineModel.DESCRIPTION_COLUMN, wineModel.getDescription());
            values.put(WineModel.COMPOSITION_TYPE_COLUMN, wineModel.getCompositionType());
            values.put(WineModel.TASTING_NOTE_ID_COLUMN, wineModel.getTastingNoteId());
            values.put(WineModel.FOOD_PAIRINGS_COLUMN, wineModel.getFoodPairings());
            values.put(WineModel.ALCOHOL_CONTENT_COLUMN, wineModel.getAlcoholContent());
            values.put(WineModel.VOLUME_COLUMN, wineModel.getVolume());
            values.put(WineModel.GRAPE_ID_COLUMN, wineModel.getGrapeId());
            values.put(WineModel.ACIDITY_COLUMN, wineModel.getAcidity());
            values.put(WineModel.IDEAL_TEMPERATURE_COLUMN, wineModel.getIdealTemperatureCelsius());
            values.put(WineModel.AGING_POTENTIAL_COLUMN, wineModel.getAgingPotential());
            values.put(WineModel.UNIT_PRICE_COLUMN, wineModel.getUnitPrice());
            rows = db.update(WineModel.TABLE_NAME, values,
                    WineModel.WINE_ID_COLUMN + " = ?",
                    new String[]{String.valueOf(wineModel.getWineId())});
        } finally {
            Close();
        }
        return rows;
    }

    // DELETE
    public int delete(long id) {
        int rows = 0;
        try {
            Open();
            rows = db.delete(WineModel.TABLE_NAME,
                    WineModel.WINE_ID_COLUMN + " = ?",
                    new String[]{String.valueOf(id)});
        } finally {
            Close();
        }
        return rows;
    }
}