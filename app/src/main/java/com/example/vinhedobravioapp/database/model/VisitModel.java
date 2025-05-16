package com.example.vinhedobravioapp.database.model;

public class VisitModel {

    public static String TABLE_NAME = "tb_visit";

    public static String
        COLUMN_ID = "visit_id",
        COLUMN_CUSTOMER_ID = "customer_id",
        COLUMN_DATE_TIME = "date_time",
        COLUMN_LOCATION = "location",
        COLUMN_WINES = "wines",
        COLUMN_USER_ID = "user_id";

    public static String CREATE_TABLE =
        "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + COLUMN_CUSTOMER_ID + " INTEGER NOT NULL, "
        + COLUMN_DATE_TIME + " DATETIME NOT NULL, "
        + COLUMN_LOCATION + " TEXT NOT NULL, "
        + COLUMN_WINES + " TEXT NOT NULL, "
        + COLUMN_USER_ID + " INTEGER NOT NULL"
        + ");";

    public static String DROP_TABLE =
        "DROP TABLE IF EXISTS " + TABLE_NAME;

    // Class fields
    private long visitId;
    private long customerId;
    private String dateTime;
    private String location;
    private String wines;
    private long userId;

    // Getters and Setters
    public long getVisitId() { return visitId; }
    public void setVisitId(long visitId) { this.visitId = visitId; }
    public long getCustomerId() { return customerId; }
    public void setCustomerId(long customerId) { this.customerId = customerId; }
    public String getDateTime() { return dateTime; }
    public void setDateTime(String dateTime) { this.dateTime = dateTime; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getWines() { return wines; }
    public void setWines(String wines) { this.wines = wines; }
    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }
}