package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.FoodPairingModel;
import com.example.vinhedobravioapp.database.model.WineFoodPairingModel;

import java.util.ArrayList;
import java.util.List;

public class WineFoodPairingDAO extends AbstrataDAO{
    public WineFoodPairingDAO(Context context) {
        helper = new DPOpenHelper(context);
    }

    public long insert(WineFoodPairingModel model) {
        long result = -1;
        try {
            Open();
            ContentValues v = new ContentValues();
            v.put(WineFoodPairingModel.COLUMN_WINE_ID, model.getWineId());
            v.put(WineFoodPairingModel.COLUMN_PAIRING_ID, model.getFoodPairingId());
            result = db.insert(WineFoodPairingModel.TABLE_NAME, null, v);
        } finally {
            Close();
        }
        return result;
    }

    public List<String> getPairingNamesByWineId(long wineId, Context context) {
        List<String> pairingNames = new ArrayList<>();
        FoodPairingDAO foodPairingDAO = new FoodPairingDAO(context);

        try {
            Open();
            Cursor c = db.query(WineFoodPairingModel.TABLE_NAME,
                    new String[]{WineFoodPairingModel.COLUMN_PAIRING_ID},
                    WineFoodPairingModel.COLUMN_WINE_ID + " = ?",
                    new String[]{String.valueOf(wineId)},
                    null, null, null);

            if (c != null && c.moveToFirst()) {
                do {
                    long pairingId = c.getLong(c.getColumnIndexOrThrow(WineFoodPairingModel.COLUMN_PAIRING_ID));
                    FoodPairingModel pairing = foodPairingDAO.getById(pairingId); // Você precisa implementar getById no FoodPairingDAO
                    if (pairing != null) {
                        pairingNames.add(pairing.getName());
                    }
                } while (c.moveToNext());
                c.close();
            }
        } finally {
            Close();
        }
        return pairingNames;
    }


    public List<WineFoodPairingModel> getAll() {
        List<WineFoodPairingModel> list = new ArrayList<>();
        try {
            Open();
            Cursor c = db.query(WineFoodPairingModel.TABLE_NAME, null,
                    null, null, null, null, null);
            if (c != null && c.moveToFirst()) {
                do {
                    WineFoodPairingModel m = new WineFoodPairingModel();
                    m.setWineId(c.getLong(c.getColumnIndexOrThrow(WineFoodPairingModel.COLUMN_WINE_ID)));
                    m.setFoodPairingId(c.getLong(c.getColumnIndexOrThrow(WineFoodPairingModel.COLUMN_PAIRING_ID)));
                    list.add(m);
                } while (c.moveToNext());
                c.close();
            }
        } finally {
            Close();
        }
        return list;
    }

    public int deleteByWineId(long wineId) {
        int rows = 0;
        try {
            Open();
            rows = db.delete(
                    WineFoodPairingModel.TABLE_NAME,
                    WineFoodPairingModel.COLUMN_WINE_ID + " = ?",
                    new String[]{String.valueOf(wineId)}
            );
        } finally {
            Close();
        }
        return rows;
    }

    public int delete(long wineId, long pairingId) {
        int rows = 0;
        try {
            Open();
            rows = db.delete(
                    WineFoodPairingModel.TABLE_NAME,
                    WineFoodPairingModel.COLUMN_WINE_ID + " = ? AND " +
                            WineFoodPairingModel.COLUMN_PAIRING_ID + " = ?",
                    new String[]{String.valueOf(wineId), String.valueOf(pairingId)}
            );
        } finally {
            Close();
        }
        return rows;
    }
}
