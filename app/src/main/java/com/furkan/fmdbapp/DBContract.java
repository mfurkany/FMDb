package com.furkan.fmdbapp;
import android.provider.BaseColumns;

public class DBContract {

    private DBContract(){

    }

    public static final class MovieRatingsEntry implements BaseColumns {
        public static final String TABLE_NAME = "movieRatings";
        public static final String COLUMN_ID = "movieID";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_TITLE = "movieTitle";
    }

}
