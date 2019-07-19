package com.example.attendancemanager;

import android.app.ActionBar;
import android.provider.BaseColumns;

public class Tablevalues {
    private Tablevalues(){}
    public static final class Coloums implements BaseColumns{
        public final static String TABLE_NAME="ATTENDENCE";
        public final static String COLOUMN_SUBJECT="subject";
        public final static String COLOUMN_PERCENTAGE="percentage";
        public final static String COLOUMN_NOPRESENT="nopresent";
        public final static String COLOUMN_NOABSENT="noabsent";
        public final static String COLOUMN_AIM="aim";
        public final static String COLOUMN_BUNK="bunk";
        public final static String COLOUMN_TIME="time";
        public final static String COLOUMN_COLOR="color";
        public final static String COLOUMN_TIMESTAMP="timestamp";
    }
}
