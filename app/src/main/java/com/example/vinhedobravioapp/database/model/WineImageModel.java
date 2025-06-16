package com.example.vinhedobravioapp.database.model;

public class WineImageModel {
    public static final String TABLE_NAME = "tb_wine_image";
    public static final String COLUMN_WINE_ID = "wine_id";
    public static final String COLUMN_IMAGE_BASE64 = "image_base64";

    public static final String CREATE_TABLE =
        "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
        + COLUMN_WINE_ID + " INTEGER NOT NULL, "
        + COLUMN_IMAGE_BASE64 + " TEXT, "
        + "PRIMARY KEY (" + COLUMN_WINE_ID + "), "
        + "FOREIGN KEY (" + COLUMN_WINE_ID + ") REFERENCES tb_wine(wine_id) ON DELETE CASCADE"
        + ");";

    public static final String DROP_TABLE =
        "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    private long wineId;
    private String imageBase64;

    public long getWineId() { return wineId; }
    public void setWineId(long wineId) { this.wineId = wineId; }
    public String getImageBase64() { return imageBase64; }
    public void setImageBase64(String imageBase64) { this.imageBase64 = imageBase64; }
}
