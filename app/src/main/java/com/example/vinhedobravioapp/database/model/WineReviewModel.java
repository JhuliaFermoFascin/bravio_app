package com.example.vinhedobravioapp.database.model;

public class WineReviewModel {
    public static final String TABLE_NAME = "tb_wine_review";
    public static final String COLUMN_ID = "review_id";
    public static final String COLUMN_WINE_ID = "wine_id";
    public static final String COLUMN_REVIEW_TEXT = "review_text";
    public static final String COLUMN_RATING = "rating";

    public static final String CREATE_TABLE =
        "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + COLUMN_WINE_ID + " INTEGER NOT NULL, "
        + COLUMN_REVIEW_TEXT + " TEXT, "
        + COLUMN_RATING + " REAL, "
        + "FOREIGN KEY (" + COLUMN_WINE_ID + ") REFERENCES tb_wine(wine_id) ON DELETE CASCADE"
        + ");";

    public static final String DROP_TABLE =
        "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    private long reviewId;
    private long wineId;
    private String reviewText;
    private double rating;

    public long getReviewId() { return reviewId; }
    public void setReviewId(long reviewId) { this.reviewId = reviewId; }
    public long getWineId() { return wineId; }
    public void setWineId(long wineId) { this.wineId = wineId; }
    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    @Override
    public String toString() {
        return "WineReviewModel{" +
                "reviewId=" + reviewId +
                ", wineId=" + wineId +
                ", reviewText='" + reviewText + '\'' +
                ", rating=" + rating +
                '}';
    }
}
