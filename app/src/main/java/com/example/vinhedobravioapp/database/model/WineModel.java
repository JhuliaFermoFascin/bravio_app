package com.example.vinhedobravioapp.database.model;

import java.io.Serializable;

public class WineModel implements Serializable {

    public static String TABLE_NAME = "tb_wine";
    public static String
        WINE_ID_COLUMN = "wine_id",
        NAME_COLUMN = "name",
        WINERY_ID_COLUMN = "winery_id",
        WINE_TYPE_ID_COLUMN = "wine_type_id",
        COMMERCIAL_CATEGORY_ID_COLUMN = "commercial_category_id",
        ORIGIN_ID_COLUMN = "origin_id",
        VINTAGE_COLUMN = "vintage",
        DESCRIPTION_COLUMN = "description",
        COMPOSITION_TYPE_COLUMN = "composition_type",
        TASTING_NOTE_ID_COLUMN = "tasting_note_id",
        FOOD_PAIRINGS_COLUMN = "food_pairings",
        ALCOHOL_CONTENT_COLUMN = "alcohol_content",
        VOLUME_COLUMN = "volume_ml",
        GRAPE_ID_COLUMN = "grape_id",
        ACIDITY_COLUMN = "acidity",
        IDEAL_TEMPERATURE_COLUMN = "ideal_temperature_celsius",
        AGING_POTENTIAL_COLUMN = "aging_potential";

    public static String CREATE_TABLE_WINE =
        "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
        + WINE_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + NAME_COLUMN + " TEXT NOT NULL, "
        + WINERY_ID_COLUMN + " INTEGER, "
        + WINE_TYPE_ID_COLUMN + " INTEGER NOT NULL, "
        + COMMERCIAL_CATEGORY_ID_COLUMN + " INTEGER, "
        + ORIGIN_ID_COLUMN + " INTEGER, "
        + VINTAGE_COLUMN + " TEXT NOT NULL, "
        + DESCRIPTION_COLUMN + " TEXT, "
        + COMPOSITION_TYPE_COLUMN + " TEXT NOT NULL, "
        + TASTING_NOTE_ID_COLUMN + " INTEGER, "
        + FOOD_PAIRINGS_COLUMN + " TEXT, "
        + ALCOHOL_CONTENT_COLUMN + " REAL, "
        + VOLUME_COLUMN + " INTEGER, "
        + GRAPE_ID_COLUMN + " INTEGER, "
        + ACIDITY_COLUMN + " TEXT, "
        + IDEAL_TEMPERATURE_COLUMN + " REAL, "
        + AGING_POTENTIAL_COLUMN + " TEXT, "
        + "FOREIGN KEY (" + WINERY_ID_COLUMN + ") REFERENCES tb_winery(winery_id), "
        + "FOREIGN KEY (" + WINE_TYPE_ID_COLUMN + ") REFERENCES tb_wine_type(wine_type_id), "
        + "FOREIGN KEY (" + COMMERCIAL_CATEGORY_ID_COLUMN + ") REFERENCES tb_commercial_category(category_id), "
        + "FOREIGN KEY (" + ORIGIN_ID_COLUMN + ") REFERENCES tb_geographic_origin(origin_id), "
        + "FOREIGN KEY (" + TASTING_NOTE_ID_COLUMN + ") REFERENCES tb_tasting_note(tasting_note_id), "
        + "FOREIGN KEY (" + GRAPE_ID_COLUMN + ") REFERENCES tb_grape(grape_id)"
        + ");";

    public static String DROP_TABLE =
        "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    // Fields
    private long wineId;
    private String name;
    private Long wineryId;
    private long wineTypeId;
    private Long commercialCategoryId;
    private Long originId;
    private String vintage;
    private String description;
    private String compositionType;
    private Long tastingNoteId;
    private String foodPairings;
    private Double alcoholContent;
    private Integer volume;
    private Long grapeId;
    private String acidity;
    private Double idealTemperatureCelsius;
    private String agingPotential;

    // Getters and Setters
    public long getWineId() { return wineId; }
    public void setWineId(long wineId) { this.wineId = wineId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getWineryId() { return wineryId; }
    public void setWineryId(Long wineryId) { this.wineryId = wineryId; }
    public long getWineTypeId() { return wineTypeId; }
    public void setWineTypeId(long wineTypeId) { this.wineTypeId = wineTypeId; }
    public Long getCommercialCategoryId() { return commercialCategoryId; }
    public void setCommercialCategoryId(Long commercialCategoryId) { this.commercialCategoryId = commercialCategoryId; }
    public Long getOriginId() { return originId; }
    public void setOriginId(Long originId) { this.originId = originId; }
    public String getVintage() { return vintage; }
    public void setVintage(String vintage) { this.vintage = vintage; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCompositionType() { return compositionType; }
    public void setCompositionType(String compositionType) { this.compositionType = compositionType; }
    public Long getTastingNoteId() { return tastingNoteId; }
    public void setTastingNoteId(Long tastingNoteId) { this.tastingNoteId = tastingNoteId; }
    public String getFoodPairings() { return foodPairings; }
    public void setFoodPairings(String foodPairings) { this.foodPairings = foodPairings; }
    public Double getAlcoholContent() { return alcoholContent; }
    public void setAlcoholContent(Double alcoholContent) { this.alcoholContent = alcoholContent; }
    public Integer getVolume() { return volume; }
    public void setVolume(Integer volume) { this.volume = volume; }
    public Long getGrapeId() { return grapeId; }
    public void setGrapeId(Long grapeId) { this.grapeId = grapeId; }
    public String getAcidity() { return acidity; }
    public void setAcidity(String acidity) { this.acidity = acidity; }
    public Double getIdealTemperatureCelsius() { return idealTemperatureCelsius; }
    public void setIdealTemperatureCelsius(Double idealTemperatureCelsius) { this.idealTemperatureCelsius = idealTemperatureCelsius; }
    public String getAgingPotential() { return agingPotential; }
    public void setAgingPotential(String agingPotential) { this.agingPotential = agingPotential; }
}
