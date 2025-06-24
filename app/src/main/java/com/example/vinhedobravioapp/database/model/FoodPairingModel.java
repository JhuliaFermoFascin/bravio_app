package com.example.vinhedobravioapp.database.model;

public class FoodPairingModel {
    public static final String TABLE_NAME = "tb_food_pairing";
    public static final String COLUMN_ID = "food_pairing_id";
    public static final String COLUMN_NAME = "name";

    public static final String CREATE_TABLE_FOOD_PAIRING =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_NAME + " TEXT NOT NULL, "
                    + "UNIQUE (" + COLUMN_NAME + ")"
                    + ");";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    private long foodPairingId;
    private String name;

    public long getFoodPairingId() {
        return foodPairingId;
    }

    public void setFoodPairingId(long foodPairingId) {
        this.foodPairingId = foodPairingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FoodPairingModel{" +
                "foodPairingId=" + foodPairingId +
                ", name='" + name + '\'' +
                '}';
    }
}
