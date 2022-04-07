package com.example.mymap.utils;

import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.util.ArrayList;


public class GlobalVariables {
    public static Constants.Style currentBackgroud = Constants.Style.BG_NEN_BAN_DO;
    public static Constants.Style getCurrentForeground = Constants.Style.FG_TTQH_SO;
    public static int bottom_sheet_height = 0;
    public static ArrayList<String> LIST_DIEM_CAO_DO_NEN = new ArrayList<>();
//    public static ArrayList<DiemCaoDoFeature> DIEM_CAO_DO_NEN = new ArrayList<>();
    public static String getCurrentLanguage = "vi";
    public static float ratioProgress = 100;
    public static int height = 0;
    public static int width = 0;
    public static MapboxMap mMap;
}
