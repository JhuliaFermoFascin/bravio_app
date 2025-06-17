package com.example.vinhedobravioapp.database.model;

public class WineGrapeModel {
    public static final String TABLE_NAME = "tb_wine_grape";
    public static final String COLUMN_WINE_ID = "wine_id";
    public static final String COLUMN_GRAPE_ID = "grape_id";

    public static final String CREATE_TABLE =
        "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
        + COLUMN_WINE_ID + " INTEGER NOT NULL, "
        + COLUMN_GRAPE_ID + " INTEGER NOT NULL, "
        + "PRIMARY KEY (" + COLUMN_WINE_ID + ", " + COLUMN_GRAPE_ID + "), "
        + "FOREIGN KEY (" + COLUMN_WINE_ID + ") REFERENCES tb_wine(wine_id) ON DELETE CASCADE, "
        + "FOREIGN KEY (" + COLUMN_GRAPE_ID + ") REFERENCES tb_grape(grape_id) ON DELETE CASCADE"
        + ");";

    public static final String DROP_TABLE =
        "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    private long wineId;
    private long grapeId;

    public long getWineId() { return wineId; }
    public void setWineId(long wineId) { this.wineId = wineId; }
    public long getGrapeId() { return grapeId; }
    public void setGrapeId(long grapeId) { this.grapeId = grapeId; }

    @Override
    public String toString() {
        return "WineGrapeModel{" +
                "wineId=" + wineId +
                ", grapeId=" + grapeId +
                '}';
    }
}
