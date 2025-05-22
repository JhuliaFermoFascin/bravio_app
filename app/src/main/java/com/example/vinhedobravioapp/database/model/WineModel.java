package com.example.vinhedobravioapp.database.model;

public class WineModel {

    public static String TABLE_NAME = "tb_wine";
    public static String
            WINE_ID_COLUMN = "wine_id",
            NAME_COLUMN = "name",
        WINE_TYPE_ID_COLUMN = "wine_type_id",
            VINTAGE_COLUMN = "vintage",
            DESCRIPTION_COLUM = "description",
            TASTING_NOTE_COLUMN = "tasting_note_id",
            FOOD_PARINGS_COLUMN = "food_pairings",
            ALCOHOL_CONTENT_COLUMN = "alcohol_content",
            VOLUME_COLUMN = "volume_ml";

    public static String CREATE_TABLE_VINHO =
        "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
        + WINE_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + NAME_COLUMN + " TEXT NOT NULL, "
        + WINE_TYPE_ID_COLUMN + " INTEGER NOT NULL, "
        + VINTAGE_COLUMN + " INTEGER NOT NULL, "
        + DESCRIPTION_COLUM + " TEXT NOT NULL, "
        + TASTING_NOTE_COLUMN + " INTEGER NOT NULL, "
        + FOOD_PARINGS_COLUMN + " TEXT NOT NULL, "
        + ALCOHOL_CONTENT_COLUMN + " REAL NOT NULL, "
        + VOLUME_COLUMN + " INTEGER NOT NULL, "
        + "UNIQUE (" + WINE_ID_COLUMN + "), "
        + "FOREIGN KEY (" + WINE_TYPE_ID_COLUMN + ") REFERENCES tb_wine_type(wine_type_id) ON DELETE RESTRICT ON UPDATE CASCADE, "
        + "FOREIGN KEY (" + TASTING_NOTE_COLUMN + ") REFERENCES tb_tasting_note(tasting_note_id) ON DELETE RESTRICT ON UPDATE CASCADE"
        + ");";

    public static String DROP_TABLE =
        "DROP TABLE IF EXISTS " + TABLE_NAME;

    // Fields
    private long wineId;
    private String name;
    private long wineTypeId;
    private int vintage;
    private String description;
    private long tastingNoteId;
    private String foodPairings;
    private double alcoholContent;
    private int volume;

    // Getters and Setters
    public long getWineId() { return wineId; }
    public void setWineId(long wineId) { this.wineId = wineId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public long getWineTypeId() { return wineTypeId; }
    public void setWineTypeId(long wineTypeId) { this.wineTypeId = wineTypeId; }

    public int getVintage() { return vintage; }
    public void setVintage(int vintage) { this.vintage = vintage; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public long getTastingNoteId() { return tastingNoteId; }
    public void setTastingNoteId(long tastingNoteId) { this.tastingNoteId = tastingNoteId; }

    public String getFoodPairings() { return foodPairings; }
    public void setFoodPairings(String foodPairings) { this.foodPairings = foodPairings; }

    public double getAlcoholContent() { return alcoholContent; }
    public void setAlcoholContent(double alcoholContent) { this.alcoholContent = alcoholContent; }

    public int getVolume() { return volume; }
    public void setVolume(int volume) { this.volume = volume; }
}
