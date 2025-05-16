package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;

import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.InventoryMovementModel;

public class InventoryMovementDAO extends AbstrataDAO {

    public InventoryMovementDAO(Context context) {
        helper = new DPOpenHelper(context);
    }

    public long insert(final InventoryMovementModel model) {
        long result = -1;
        try {
            Open();
            ContentValues values = new ContentValues();
            values.put(InventoryMovementModel.COLUMN_ID, model.getMovementId());
            values.put(InventoryMovementModel.COLUMN_WINE_ID, model.getWineId());
            values.put(InventoryMovementModel.COLUMN_MOVEMENT_TYPE, model.getMovementType());
            values.put(InventoryMovementModel.COLUMN_QUANTITY, model.getQuantity());
            values.put(InventoryMovementModel.COLUMN_UNIT_PRICE, model.getUnitPrice());
            values.put(InventoryMovementModel.COLUMN_MOVEMENT_DATE, model.getMovementDate());
            values.put(InventoryMovementModel.COLUMN_DOCUMENT_REFERENCE, model.getDocumentReference());
            values.put(InventoryMovementModel.COLUMN_USER_ID, model.getUserId());
            values.put(InventoryMovementModel.COLUMN_NOTES, model.getNotes());
            result = db.insert(InventoryMovementModel.TABLE_NAME, null, values);
        } finally {
            Close();
        }
        return result;
    }
}
