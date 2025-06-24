package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.TastingNoteModel;
import com.example.vinhedobravioapp.database.model.WineTastingNoteModel;
import java.util.ArrayList;
import java.util.List;

public class WineTastingNoteDAO extends AbstrataDAO {
    public WineTastingNoteDAO(Context context) { helper = new DPOpenHelper(context); }

    public long insert(WineTastingNoteModel model) {
        long result = -1;
        try {
            Open();
            ContentValues v = new ContentValues();
            v.put(WineTastingNoteModel.COLUMN_WINE_ID, model.getWineId());
            v.put(WineTastingNoteModel.COLUMN_NOTE_ID, model.getTastingNoteId());
            result = db.insert(WineTastingNoteModel.TABLE_NAME, null, v);
        } finally { Close(); }
        return result;
    }

    public List<WineTastingNoteModel> getAll() {
        List<WineTastingNoteModel> list = new ArrayList<>();
        try {
            Open();
            Cursor c = db.query(WineTastingNoteModel.TABLE_NAME, null,
                    null, null, null, null, null);
            if (c != null && c.moveToFirst()) {
                do {
                    WineTastingNoteModel m = new WineTastingNoteModel();
                    m.setWineId(c.getLong(c.getColumnIndexOrThrow(WineTastingNoteModel.COLUMN_WINE_ID)));
                    m.setTastingNoteId(c.getLong(c.getColumnIndexOrThrow(WineTastingNoteModel.COLUMN_NOTE_ID)));
                    list.add(m);
                } while (c.moveToNext());
                c.close();
            }
        } finally { Close(); }
        return list;
    }

    public List<String> getNoteNamesByWineId(long wineId, Context context) {
        List<String> noteNames = new ArrayList<>();
        TastingNoteDAO tastingNoteDAO = new TastingNoteDAO(context);

        try {
            Open();
            Cursor c = db.query(WineTastingNoteModel.TABLE_NAME,
                    new String[]{WineTastingNoteModel.COLUMN_NOTE_ID},
                    WineTastingNoteModel.COLUMN_WINE_ID + " = ?",
                    new String[]{String.valueOf(wineId)},
                    null, null, null);

            if (c != null && c.moveToFirst()) {
                do {
                    long noteId = c.getLong(c.getColumnIndexOrThrow(WineTastingNoteModel.COLUMN_NOTE_ID));
                    TastingNoteModel note = tastingNoteDAO.getById(noteId);
                    if (note != null) {
                        noteNames.add(note.getNote());
                    }
                } while (c.moveToNext());
                c.close();
            }
        } finally {
            Close();
        }
        return noteNames;
    }

    public int deleteByWineId(long wineId) {
        int rows = 0;
        try {
            Open();
            rows = db.delete(
                    WineTastingNoteModel.TABLE_NAME,
                    WineTastingNoteModel.COLUMN_WINE_ID + " = ?",
                    new String[]{String.valueOf(wineId)}
            );
        } finally {
            Close();
        }
        return rows;
    }


    public int delete(long wineId, long noteId) {
        int rows = 0;
        try {
            Open();
            rows = db.delete(
                    WineTastingNoteModel.TABLE_NAME,
                    WineTastingNoteModel.COLUMN_WINE_ID + " = ? AND " +
                            WineTastingNoteModel.COLUMN_NOTE_ID + " = ?",
                    new String[]{ String.valueOf(wineId), String.valueOf(noteId) }
            );
        } finally { Close(); }
        return rows;
    }
}
