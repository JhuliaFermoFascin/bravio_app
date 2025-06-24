package com.example.vinhedobravioapp.database.model;

public class WineFoodPairingModel {
    public static final String TABLE_NAME = "tb_wine_food_pairing";
    public static final String COLUMN_WINE_ID = "wine_id";
    public static final String COLUMN_PAIRING_ID = "food_pairing_id";

    public static final String CREATE_TABLE_WINE_FOOD_PAIRING =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                    + COLUMN_WINE_ID + " INTEGER NOT NULL, "
                    + COLUMN_PAIRING_ID + " INTEGER NOT NULL, "
                    + "PRIMARY KEY (" + COLUMN_WINE_ID + ", " + COLUMN_PAIRING_ID + "), "
                    + "FOREIGN KEY (" + COLUMN_WINE_ID + ") REFERENCES tb_wine(wine_id) ON DELETE CASCADE, "
                    + "FOREIGN KEY (" + COLUMN_PAIRING_ID + ") REFERENCES tb_food_pairing(food_pairing_id)"
                    + ");";

    private long wineId;
    private long foodPairingId;

    public long getWineId() {
        return wineId;
    }

    public void setWineId(long wineId) {
        this.wineId = wineId;
    }

    public long getFoodPairingId() {
        return foodPairingId;
    }

    public void setFoodPairingId(long foodPairingId) {
        this.foodPairingId = foodPairingId;
    }

    @Override
    public String toString() {
        return "WineFoodPairingModel{" +
                "wineId=" + wineId +
                ", foodPairingId=" + foodPairingId +
                '}';
    }
}
