package com.example.vinhedobravioapp.database.model;

public class WineryModel {
    public static final String TABLE_NAME = "tb_winery";
    public static final String COLUMN_ID = "winery_id";
    public static final String COLUMN_NAME = "name";

    public static final String CREATE_TABLE =
        "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + COLUMN_NAME + " TEXT NOT NULL UNIQUE"
        + ");";

    public static final String DROP_TABLE =
        "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    private long wineryId;
    private String name;

    public long getWineryId() { return wineryId; }
    public void setWineryId(long wineryId) { this.wineryId = wineryId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
