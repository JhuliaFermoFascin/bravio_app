package com.example.vinhedobravioapp.database.model;

public class WineModel {

    public static String TABLE_NAME = "tb_wine";
    public static String
        COLUMN_ID = "wine_id",
        COLUMN_NAME = "name",
        COLUMN_WINE_TYPE_ID = "wine_type_id",
        COLUMN_VINTAGE = "vintage",
        COLUMN_DESCRIPTION = "description",
        COLUMN_TASTING_NOTE_ID = "tasting_note_id",
        COLUMN_FOOD_PAIRINGS = "food_pairings",
        COLUMN_ALCOHOL_CONTENT = "alcohol_content",
        COLUMN_VOLUME = "volume_ml";

    public static String CREATE_TABLE_VINHO =
        "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + COLUMN_NAME + " TEXT NOT NULL, "
        + COLUMN_WINE_TYPE_ID + " INTEGER NOT NULL, "
        + COLUMN_VINTAGE + " INTEGER NOT NULL, "
        + COLUMN_DESCRIPTION + " TEXT NOT NULL, "
        + COLUMN_TASTING_NOTE_ID + " INTEGER NOT NULL, "
        + COLUMN_FOOD_PAIRINGS + " TEXT NOT NULL, "
        + COLUMN_ALCOHOL_CONTENT + " REAL NOT NULL, "
        + COLUMN_VOLUME + " INTEGER NOT NULL, "
        + "UNIQUE (" + COLUMN_ID + "), "
        + "FOREIGN KEY (" + COLUMN_WINE_TYPE_ID + ") REFERENCES tb_wine_type(wine_type_id) ON DELETE RESTRICT ON UPDATE CASCADE, "
        + "FOREIGN KEY (" + COLUMN_TASTING_NOTE_ID + ") REFERENCES tb_tasting_note(tasting_note_id) ON DELETE RESTRICT ON UPDATE CASCADE"
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
