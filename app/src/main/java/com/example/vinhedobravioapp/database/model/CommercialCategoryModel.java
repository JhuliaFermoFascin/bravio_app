package com.example.vinhedobravioapp.database.model;

public class CommercialCategoryModel {
    public static final String TABLE_NAME = "tb_commercial_category";
    public static final String COLUMN_ID = "category_id";
    public static final String COLUMN_NAME = "name";

    public static final String CREATE_TABLE =
        "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + COLUMN_NAME + " TEXT NOT NULL UNIQUE"
        + ");";

    public static final String DROP_TABLE =
        "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    private long categoryId;
    private String name;

    public long getCategoryId() { return categoryId; }
    public void setCategoryId(long categoryId) { this.categoryId = categoryId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "CommercialCategoryModel{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                '}';
    }
}
