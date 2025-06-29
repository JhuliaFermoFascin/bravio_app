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
COMPOSITION_TYPE_ID_COLUMN = "composition_type_id",
ALCOHOL_CONTENT_COLUMN = "alcohol_content",
VOLUME_COLUMN = "volume_ml",
ACIDITY_COLUMN = "acidity",
IDEAL_TEMPERATURE_COLUMN = "ideal_temperature_celsius",
AGING_POTENTIAL_COLUMN = "aging_potential",
UNIT_PRICE_COLUMN = "unit_price";

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
    + COMPOSITION_TYPE_ID_COLUMN + " INTEGER NOT NULL, "
    + ALCOHOL_CONTENT_COLUMN + " REAL, "
    + VOLUME_COLUMN + " INTEGER, "
    + ACIDITY_COLUMN + " TEXT, "
    + IDEAL_TEMPERATURE_COLUMN + " REAL, "
    + AGING_POTENTIAL_COLUMN + " TEXT, "
    + UNIT_PRICE_COLUMN + " REAL, "
    + "FOREIGN KEY (" + WINERY_ID_COLUMN + ") REFERENCES tb_winery(winery_id), "
    + "FOREIGN KEY (" + WINE_TYPE_ID_COLUMN + ") REFERENCES tb_wine_type(wine_type_id), "
    + "FOREIGN KEY (" + COMMERCIAL_CATEGORY_ID_COLUMN + ") REFERENCES tb_commercial_category(category_id), "
    + "FOREIGN KEY (" + ORIGIN_ID_COLUMN + ") REFERENCES tb_geographic_origin(origin_id), "
    + "FOREIGN KEY (" + COMPOSITION_TYPE_ID_COLUMN + ") REFERENCES tb_composition_type(composition_type_id)"
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
    private long compositionTypeId;
    private Double alcoholContent;
    private Integer volume;
    private String acidity;
    private Double idealTemperatureCelsius;
    private String agingPotential;
    private Double unitPrice;

    // Default constructor
    public WineModel() {}

    // Full constructor
    public WineModel(long wineId, String name, Long wineryId, long wineTypeId, Long commercialCategoryId,
                     Long originId, String vintage, String description, long compositionTypeId,
                     Double alcoholContent, Integer volume,
                     String acidity, Double idealTemperatureCelsius, String agingPotential, Double unitPrice) {

        this.wineId = wineId;
        this.name = name;
        this.wineryId = wineryId;
        this.wineTypeId = wineTypeId;
        this.commercialCategoryId = commercialCategoryId;
        this.originId = originId;
        this.vintage = vintage;
        this.description = description;
        this.compositionTypeId = compositionTypeId;
        this.alcoholContent = alcoholContent;
        this.volume = volume;
        this.acidity = acidity;
        this.idealTemperatureCelsius = idealTemperatureCelsius;
        this.agingPotential = agingPotential;
        this.unitPrice = unitPrice;
    }

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
    public long getCompositionTypeId() { return compositionTypeId; }
    public void setCompositionTypeId(long compositionTypeId) { this.compositionTypeId = compositionTypeId; }
    public Double getAlcoholContent() { return alcoholContent; }
    public void setAlcoholContent(Double alcoholContent) { this.alcoholContent = alcoholContent; }
    public Integer getVolume() { return volume; }
    public void setVolume(Integer volume) { this.volume = volume; }
    public String getAcidity() { return acidity; }
    public void setAcidity(String acidity) { this.acidity = acidity; }
    public Double getIdealTemperatureCelsius() { return idealTemperatureCelsius; }
    public void setIdealTemperatureCelsius(Double idealTemperatureCelsius) { this.idealTemperatureCelsius = idealTemperatureCelsius; }
    public String getAgingPotential() { return agingPotential; }
    public void setAgingPotential(String agingPotential) { this.agingPotential = agingPotential; }
    public Double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }

    @Override
    public String toString() {
        return "WineModel{" +
                "wineId=" + wineId +
                ", name='" + name + '\'' +
                ", wineryId=" + wineryId +
                ", wineTypeId=" + wineTypeId +
                ", commercialCategoryId=" + commercialCategoryId +
                ", originId=" + originId +
                ", vintage='" + vintage + '\'' +
                ", description='" + description + '\'' +
                ", compositionTypeId='" + compositionTypeId + '\'' +
                ", alcoholContent=" + alcoholContent +
                ", volume=" + volume +
                ", acidity='" + acidity + '\'' +
                ", idealTemperatureCelsius=" + idealTemperatureCelsius +
                ", agingPotential='" + agingPotential + '\'' +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
