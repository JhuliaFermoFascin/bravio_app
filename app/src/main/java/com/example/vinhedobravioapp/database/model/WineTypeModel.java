package com.example.vinhedobravioapp.database.model;

public class WineTypeModel {

    public static String TABLE_NAME = "tb_wine_type";

    public static String
        COLUMN_ID = "wine_type_id",
        COLUMN_TYPE_NAME = "type_name";

    public static String CREATE_TABLE_WINE_TYPE =
        "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + COLUMN_TYPE_NAME + " TEXT NOT NULL, "
        + "UNIQUE (" + COLUMN_TYPE_NAME + ")"
        + ");";

    public static String DROP_TABLE =
        "DROP TABLE IF EXISTS " + TABLE_NAME;

    // Class fields
    private long wineTypeId;
    private String typeName;

    // Getters and Setters
    public long getWineTypeId() {
        return wineTypeId;
    }

    public void setWineTypeId(long wineTypeId) {
        this.wineTypeId = wineTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
