package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;

import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.TastingNoteModel;

public class TastingNoteDAO extends AbstrataDAO {

    public TastingNoteDAO(Context context) {
        helper = new DPOpenHelper(context);
    }

    public long insert(final TastingNoteModel tastingNoteModel) {
        long result = -1;
        try {
            Open();
            ContentValues values = new ContentValues();
            values.put(TastingNoteModel.COLUMN_ID, tastingNoteModel.getTastingNoteId());
            values.put(TastingNoteModel.COLUMN_NOTE, tastingNoteModel.getNote());
            result = db.insert(TastingNoteModel.TABLE_NAME, null, values);
        } finally {
            Close();
        }
        return result;
    }
}
