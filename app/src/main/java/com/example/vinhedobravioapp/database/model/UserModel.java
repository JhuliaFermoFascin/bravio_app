package com.example.vinhedobravioapp.database.model;

public class UserModel {

    public static String TABLE_NAME = "tb_user";

    public static String
        COLUMN_ID = "user_id",
        COLUMN_NAME = "name",
        COLUMN_EMAIL = "email",
        COLUMN_PASSWORD = "password",
        COLUMN_IS_ADMIN = "is_admin",
        COLUMN_STATUS = "status",
        COLUMN_CREATED_AT = "created_at",
        COLUMN_LAST_UPDATE = "last_update",
        COLUMN_LAST_LOGIN = "last_login";

    public static String CREATE_TABLE_USER =
        "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + COLUMN_NAME + " TEXT NOT NULL, "
        + COLUMN_EMAIL + " TEXT NOT NULL, "
        + COLUMN_PASSWORD + " TEXT NOT NULL, "
        + COLUMN_IS_ADMIN + " INTEGER NOT NULL, "
        + COLUMN_STATUS + " INTEGER NOT NULL, "
        + COLUMN_CREATED_AT + " DATE NOT NULL, "
        + COLUMN_LAST_UPDATE + " DATE DEFAULT NULL, "
        + COLUMN_LAST_LOGIN + " DATE DEFAULT NULL"
        + ");";

    public static String DROP_TABLE =
        "DROP TABLE IF EXISTS " + TABLE_NAME;

    // Class fields
    private long userId;
    private String name;
    private String email;
    private String password;
    private int isAdmin;
    private int status;
    private String createdAt;
    private String lastUpdate;
    private String lastLogin;

    // Getters and Setters
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }
}
