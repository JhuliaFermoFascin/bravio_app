package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;
import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.WineReviewModel;

public class WineReviewDAO extends AbstrataDAO {
    public WineReviewDAO(Context context) { helper = new DPOpenHelper(context); }
    public long insert(final WineReviewModel model) {
        long result = -1;
        try { Open();
            ContentValues values = new ContentValues();
            values.put(WineReviewModel.COLUMN_WINE_ID, model.getWineId());
            values.put(WineReviewModel.COLUMN_REVIEW_TEXT, model.getReviewText());
            values.put(WineReviewModel.COLUMN_RATING, model.getRating());
            result = db.insert(WineReviewModel.TABLE_NAME, null, values);
        } finally { Close(); }
        return result;
    }
}
