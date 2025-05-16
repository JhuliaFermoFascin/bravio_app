package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;

import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.VisitModel;

public class VisitDAO extends AbstrataDAO {

    public VisitDAO(Context context) {
        helper = new DPOpenHelper(context);
    }

    public long insert(final VisitModel visitModel) {
        long result = -1;
        try {
            Open();
            ContentValues values = new ContentValues();
            values.put(VisitModel.COLUMN_ID, visitModel.getVisitId());
            values.put(VisitModel.COLUMN_CUSTOMER_ID, visitModel.getCustomerId());
            values.put(VisitModel.COLUMN_DATE_TIME, visitModel.getDateTime());
            values.put(VisitModel.COLUMN_LOCATION, visitModel.getLocation());
            values.put(VisitModel.COLUMN_WINES, visitModel.getWines());
            values.put(VisitModel.COLUMN_USER_ID, visitModel.getUserId());
            result = db.insert(VisitModel.TABLE_NAME, null, values);
        } finally {
            Close();
        }
        return result;
    }
}
