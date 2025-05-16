package com.example.vinhedobravioapp.database.model;

public class WineModel {

    public static String TABLE_NAME = "tb_wine";
    public static String
            WINE_ID_COLUMN = "wine_id",
            NAME_COLUMN = "name",
            TYPE_COLUMN = "wine_type",
            VINTAGE_COLUMN = "vintage",
            DESCRIPTION_COLUM = "description",
            TASTING_NOTE_COLUMN = "tasting_note",
            FOOD_PARINGS_COLUMN = "food_parings",
            ALCOHOL_CONTENT_COLUMN = "alcohol_content",
            VOLUME_COLUMN = "volume";

    public static String CREATE_WINE_TABLE=
            "CREATE TABLE " + TABLE_NAME
                    + " ( "
                    + WINE_ID_COLUMN + " INTEGER PRIMARY KEY NOT NULL AUTOINCREMENT, "
                    + NAME_COLUMN + " TEXT NOT NULL, "
                    + TYPE_COLUMN + " INTEGER NOT NULL, "
                    + VINTAGE_COLUMN + " INTEGER NOT NULL, "
                    + DESCRIPTION_COLUM + " TEXT NOT NULL, "
                    + TASTING_NOTE_COLUMN + " INTEGER NOT NULL, "
                    + FOOD_PARINGS_COLUMN + " TEXT NOT NULL, "
                    + ALCOHOL_CONTENT_COLUMN + " DECIMAL NOT NULL, "
                    + VOLUME_COLUMN + " INTEGER NOT NULL, "
                    + "FOREIGN KEY(" + TYPE_COLUMN + ") REFERENCES tb_wine_type(id)"
                    + "FOREIGN KEY(" + TASTING_NOTE_COLUMN + ") REFERENCES tb_tasting_note(id)"
                    + " );";

    public static String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    private long id;
    private String name;
    private Integer type;
    private Integer vintage;
    private String description;
    private String tasting_note;
    private String food_parings;
    private Double alcohol_content;
    private Integer volume;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getVintage() {
        return vintage;
    }

    public void setVintage(Integer vintage) {
        this.vintage = vintage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTasting_note() {
        return tasting_note;
    }

    public void setTasting_note(String tasting_note) {
        this.tasting_note = tasting_note;
    }

    public String getFood_parings() {
        return food_parings;
    }

    public void setFood_parings(String food_parings) {
        this.food_parings = food_parings;
    }

    public Double getAlcohol_content() {
        return alcohol_content;
    }

    public void setAlcohol_content(Double alcohol_content) {
        this.alcohol_content = alcohol_content;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }
}


