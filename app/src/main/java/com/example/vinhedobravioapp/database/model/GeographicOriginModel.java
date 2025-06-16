package com.example.vinhedobravioapp.database.model;

public class GeographicOriginModel {
    public static final String TABLE_NAME = "tb_geographic_origin";
    public static final String COLUMN_ID = "origin_id";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_REGION = "region";

    public static final String CREATE_TABLE =
        "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + COLUMN_COUNTRY + " TEXT NOT NULL, "
        + COLUMN_REGION + " TEXT"
        + ");";

    public static final String DROP_TABLE =
        "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    private long originId;
    private String country;
    private String region;

    public long getOriginId() { return originId; }
    public void setOriginId(long originId) { this.originId = originId; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    @Override
    public String toString() {
        return "GeographicOriginModel{" +
                "originId=" + originId +
                ", country='" + country + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
