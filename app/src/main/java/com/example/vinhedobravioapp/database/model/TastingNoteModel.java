package com.example.vinhedobravioapp.database.model;

public class TastingNoteModel {

    public static String TABLE_NAME = "tb_tasting_note";

    public static String
        COLUMN_ID = "tasting_note_id",
        COLUMN_NOTE = "note";

    public static String CREATE_TABLE_TASTING_NOTE =
        "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + COLUMN_NOTE + " TEXT NOT NULL, "
        + "UNIQUE (" + COLUMN_NOTE + ")"
        + ");";

    public static String DROP_TABLE =
        "DROP TABLE IF EXISTS " + TABLE_NAME;

    // Class fields
    private long tastingNoteId;
    private String note;

    // Getters and Setters
    public long getTastingNoteId() {
        return tastingNoteId;
    }

    public void setTastingNoteId(long tastingNoteId) {
        this.tastingNoteId = tastingNoteId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "TastingNoteModel{" +
                "tastingNoteId=" + tastingNoteId +
                ", note='" + note + '\'' +
                '}';
    }
}
