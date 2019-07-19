package com.example.attendancemanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.attendancemanager.Tablevalues.*;

public class sqlitehelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="attendence.db";
    public static final int DATABASE_VERSION=1;
    public sqlitehelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
              final String sql_query="CREATE TABLE " +
                      Coloums.TABLE_NAME + " (" +
                      Coloums._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                      Coloums.COLOUMN_SUBJECT + " TEXT NOT NULL, " +
                      Coloums.COLOUMN_PERCENTAGE + " TEXT, " +
                      Coloums.COLOUMN_NOPRESENT + " TEXT, " +
                      Coloums.COLOUMN_NOABSENT + " TEXT, " +
                      Coloums.COLOUMN_AIM + " TEXT, " +
                      Coloums.COLOUMN_BUNK + " TEXT," +
                      Coloums.COLOUMN_TIME + " TEXT, " +
                      Coloums.COLOUMN_COLOR + " TEXT , " +
                      Coloums.COLOUMN_TIMESTAMP + " DEFAULT CURRENT_TIMESTAMP " +
                      ");";
              db.execSQL(sql_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
             db.execSQL("DROP TABLE IF EXISTS " + Coloums.TABLE_NAME);
             onCreate(db);
    }
}
