package com.cpunisher.pilot.util;

import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RankHelper {

    private static SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static void recordScore(SharedPreferences sharedPreferences, int score) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Date date = new Date();

        editor.putString(ft.format(date), String.valueOf(score));
        editor.commit();
    }

    public static void getAllRecords(SharedPreferences sharedPreferences) {

    }
}
