package com.example.vinhedobravioapp.database.model;

public class OrderModel {

    public static String TABLE_NAME = "tb_order";

    public static String
        COLUMN_ID = "order_id",
        COLUMN_CUSTOMER_ID = "customer_id",
        COLUMN_DATE = "date",
        COLUMN_TOTAL = "total",
        COLUMN_STATUS = "status",
        COLUMN_USER_ID = "user_id";

    public static String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_CUSTOMER_ID + " INTEGER NOT NULL, "
                    + COLUMN_TOTAL + " DOUBLE NOT NULL, "
                    + COLUMN_DATE + " TEXT NOT NULL, "
                    + COLUMN_STATUS + " TEXT NOT NULL, "
                    + COLUMN_USER_ID + " INTEGER NOT NULL, "
                    + "FOREIGN KEY (" + COLUMN_CUSTOMER_ID + ") REFERENCES tb_customer(customer_id) ON UPDATE CASCADE, "
                    + "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES tb_user(user_id) ON UPDATE CASCADE"
                    + ");";

    public static String DROP_TABLE =
        "DROP TABLE IF EXISTS " + TABLE_NAME;

    // Fields
    private long orderId;
    private long customerId;
    private String date;
    private Double total;
    private String status;
    private long userId;

    public long getOrderId() { return orderId; }
    public void setOrderId(long orderId) { this.orderId = orderId; }
    public long getCustomerId() { return customerId; }
    public void setCustomerId(long customerId) { this.customerId = customerId; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }
    public Double getTotal() {return total;}
    public void setTotal(Double total) {this.total = total;}
}