package com.example.vinhedobravioapp.database.model;

public class GrapeModel {
    public static final String TABLE_NAME = "tb_grape";
    public static final String COLUMN_ID = "grape_id";
    public static final String COLUMN_NAME = "name";

    public static final String CREATE_TABLE =
        "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + COLUMN_NAME + " TEXT NOT NULL UNIQUE"
        + ");";

    public static final String DROP_TABLE =
        "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    private long grapeId;
    private String name;

    public long getGrapeId() { return grapeId; }
    public void setGrapeId(long grapeId) { this.grapeId = grapeId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "GrapeModel{" +
                "grapeId=" + grapeId +
                ", name='" + name + '\'' +
                '}';
    }
}
