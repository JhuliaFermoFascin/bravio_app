package com.example.vinhedobravioapp.database.model;

public class OrderItemModel {

    public static String TABLE_NAME = "tb_order_item";

    public static String
        COLUMN_ID = "order_item_id",
        COLUMN_WINE_ID = "wine_id",
        COLUMN_VALUE = "value",
        COLUMN_QUANTITY = "quantity",
        COLUMN_ORDER_ID = "order_id";

    public static String CREATE_TABLE =
        "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + COLUMN_WINE_ID + " INTEGER NOT NULL, "
        + COLUMN_VALUE + " REAL NOT NULL, "
        + COLUMN_QUANTITY + " INTEGER NOT NULL, "
        + COLUMN_ORDER_ID + " INTEGER NOT NULL, "
        + "UNIQUE (" + COLUMN_ID + ")"
        + ");";

    public static String DROP_TABLE =
        "DROP TABLE IF EXISTS " + TABLE_NAME;

    // Fields
    private long orderItemId;
    private long wineId;
    private double value;
    private int quantity;
    private long orderId;

    // Getters and Setters
    public long getOrderItemId() { return orderItemId; }
    public void setOrderItemId(long orderItemId) { this.orderItemId = orderItemId; }
    public long getWineId() { return wineId; }
    public void setWineId(long wineId) { this.wineId = wineId; }
    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public long getOrderId() { return orderId; }
    public void setOrderId(long orderId) { this.orderId = orderId; }
}