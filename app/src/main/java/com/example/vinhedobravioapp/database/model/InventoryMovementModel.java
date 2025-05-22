package com.example.vinhedobravioapp.database.model;

public class InventoryMovementModel {

    public static String TABLE_NAME = "tb_inventory_movement";

    public static String
        COLUMN_ID = "movement_id",
        COLUMN_WINE_ID = "wine_id",
        COLUMN_MOVEMENT_TYPE = "movement_type",
        COLUMN_QUANTITY = "quantity",
        COLUMN_UNIT_PRICE = "unit_price",
        COLUMN_MOVEMENT_DATE = "movement_date",
        COLUMN_DOCUMENT_REFERENCE = "document_reference",
        COLUMN_USER_ID = "user_id",
        COLUMN_NOTES = "notes";

    public static String CREATE_TABLE =
        "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + COLUMN_WINE_ID + " INTEGER NOT NULL, "
        + COLUMN_MOVEMENT_TYPE + " TEXT NOT NULL, "
        + COLUMN_QUANTITY + " INTEGER NOT NULL, "
        + COLUMN_UNIT_PRICE + " REAL NOT NULL, "
        + COLUMN_MOVEMENT_DATE + " DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, "
        + COLUMN_DOCUMENT_REFERENCE + " TEXT DEFAULT NULL, "
        + COLUMN_USER_ID + " INTEGER NOT NULL, "
        + COLUMN_NOTES + " TEXT DEFAULT NULL"
        + "FOREIGN KEY (" + COLUMN_WINE_ID + ") REFERENCES tb_wine(wine_id) ON UPDATE CASCADE, "
        + "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES tb_user(user_id) ON UPDATE CASCADE, "
        + ");";

    public static String DROP_TABLE =
        "DROP TABLE IF EXISTS " + TABLE_NAME;

    // Class fields
    private long movementId;
    private long wineId;
    private String movementType;
    private int quantity;
    private double unitPrice;
    private String movementDate;
    private String documentReference;
    private long userId;
    private String notes;

    // Getters and Setters
    public long getMovementId() { return movementId; }
    public void setMovementId(long movementId) { this.movementId = movementId; }
    public long getWineId() { return wineId; }
    public void setWineId(long wineId) { this.wineId = wineId; }
    public String getMovementType() { return movementType; }
    public void setMovementType(String movementType) { this.movementType = movementType; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    public String getMovementDate() { return movementDate; }
    public void setMovementDate(String movementDate) { this.movementDate = movementDate; }
    public String getDocumentReference() { return documentReference; }
    public void setDocumentReference(String documentReference) { this.documentReference = documentReference; }
    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}