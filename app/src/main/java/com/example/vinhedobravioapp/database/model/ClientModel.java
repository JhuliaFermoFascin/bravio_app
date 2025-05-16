package com.example.vinhedobravioapp.database.model;

public class ClientModel {

    public static String TABLE_NAME = "tb_customer";

    public static String
        COLUMN_ID = "customer_id",
        COLUMN_NAME_COMPANY = "name_company_name",
        COLUMN_CPF_CNPJ = "cpf_cnpj",
        COLUMN_ADDRESS = "address",
        COLUMN_REGION = "region",
        COLUMN_PHONE = "phone",
        COLUMN_EMAIL = "email";

    public static String CREATE_TABLE_CUSTOMER =
        "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + COLUMN_NAME_COMPANY + " TEXT NOT NULL, "
        + COLUMN_CPF_CNPJ + " TEXT NOT NULL, "
        + COLUMN_ADDRESS + " TEXT NOT NULL, "
        + COLUMN_REGION + " TEXT NOT NULL, "
        + COLUMN_PHONE + " TEXT NOT NULL, "
        + COLUMN_EMAIL + " TEXT NOT NULL, "
        + "UNIQUE (" + COLUMN_CPF_CNPJ + "), "
        + "UNIQUE (" + COLUMN_ID + ")"
        + ");";

    public static String DROP_TABLE =
        "DROP TABLE IF EXISTS " + TABLE_NAME;

    // Class fields
    private long customerId;
    private String nameCompanyName;
    private String cpfCnpj;
    private String address;
    private String region;
    private String phone;
    private String email;

    // Getters and Setters
    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getNameCompanyName() {
        return nameCompanyName;
    }

    public void setNameCompanyName(String nameCompanyName) {
        this.nameCompanyName = nameCompanyName;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
