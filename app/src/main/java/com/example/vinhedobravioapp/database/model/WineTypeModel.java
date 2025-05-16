package com.example.vinhedobravioapp.database.model;

public class WineTypeModel {

    public static String TABLE_NAME = "tb_wine_type";

    public static String
        WINE_TYPE_ID_COLUMN = "wine_type_id",
        TYPE_NAME_COLUMN = "type_name";

    public static String CREATE_WINE_TYPE_TABLE=
            "CREATE TABLE " + TABLE_NAME
                    + " ( "
                    + WINE_TYPE_ID_COLUMN + " INTEGER PRIMARY KEY NOT NULL AUTOINCREMENT, "
                    + TYPE_NAME_COLUMN + " TEXT NOT NULL UNIQUE, "
                    + " );";

    public static String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    private long type_id;
    private String wine_type;

    public long getType_id() {
        return type_id;
    }

    public void setType_id(long type_id) {
        this.type_id = type_id;
    }

    public String getWine_type() {
        return wine_type;
    }

    public void setWine_type(String wine_type) {
        this.wine_type = wine_type;
    }
}
