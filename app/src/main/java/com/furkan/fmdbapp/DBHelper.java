package com.furkan.fmdbapp;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.furkan.fmdbapp.DBContract.*;

public class DBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME="movieRatings.db";
    public static final int DATABASE_VERSION = 1;

    public DBHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db){
        final String SQL_CREATE_MOVIERATINGS_TABLE = "CREATE TABLE " +
                MovieRatingsEntry.TABLE_NAME + " (" +
                MovieRatingsEntry.COLUMN_TITLE + " TEXT, " +
                MovieRatingsEntry.COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieRatingsEntry.COLUMN_RATING + " REAL " +
                ");";

        db.execSQL(SQL_CREATE_MOVIERATINGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ MovieRatingsEntry.TABLE_NAME);
        onCreate(db);

    }

}