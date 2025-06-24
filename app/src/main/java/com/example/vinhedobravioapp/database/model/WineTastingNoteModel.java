package com.example.vinhedobravioapp.database.model;

public class WineTastingNoteModel {
    public static final String TABLE_NAME = "tb_wine_tasting_note";
    public static final String COLUMN_WINE_ID = "wine_id";
    public static final String COLUMN_NOTE_ID = "tasting_note_id";

    public static final String CREATE_TABLE_WINE_TASTING_NOTE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                    + COLUMN_WINE_ID + " INTEGER NOT NULL, "
                    + COLUMN_NOTE_ID + " INTEGER NOT NULL, "
                    + "PRIMARY KEY (" + COLUMN_WINE_ID + ", " + COLUMN_NOTE_ID + "), "
                    + "FOREIGN KEY (" + COLUMN_WINE_ID + ") REFERENCES tb_wine(wine_id) ON DELETE CASCADE, "
                    + "FOREIGN KEY (" + COLUMN_NOTE_ID + ") REFERENCES tb_tasting_note(tasting_note_id)"
                    + ");";

    private long wineId;
    private long tastingNoteId;

    public WineTastingNoteModel() {}

    public long getWineId() {
        return wineId;
    }

    public void setWineId(long wineId) {
        this.wineId = wineId;
    }

    public long getTastingNoteId() {
        return tastingNoteId;
    }

    public void setTastingNoteId(long tastingNoteId) {
        this.tastingNoteId = tastingNoteId;
    }

    @Override
    public String toString() {
        return "WineTastingNoteModel{" +
                "wineId=" + wineId +
                ", tastingNoteId=" + tastingNoteId +
                '}';
    }
}
