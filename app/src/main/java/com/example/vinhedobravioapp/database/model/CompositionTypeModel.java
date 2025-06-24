package com.example.vinhedobravioapp.database.model;

public class CompositionTypeModel {

    public static final String TABLE_NAME = "tb_composition_type";
    public static final String COLUMN_ID = "composition_type_id";
    public static final String COLUMN_NAME = "composition_name";

    public static final String CREATE_TABLE_COMPOSITION_TYPE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_NAME + " TEXT NOT NULL UNIQUE"
                    + ");";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    private long compositionTypeId;
    private String compositionName;

    public long getCompositionTypeId() { return compositionTypeId; }
    public void setCompositionTypeId(long compositionTypeId) { this.compositionTypeId = compositionTypeId; }
    public String getCompositionName() { return compositionName; }
    public void setCompositionName(String compositionName) { this.compositionName = compositionName; }

    @Override
    public String toString() {
        return "CompositionTypeModel{" +
                "compositionTypeId=" + compositionTypeId +
                ", compositionName='" + compositionName + '\'' +
                '}';
    }
}
