package com.example.mymap.utils;
import android.app.Activity;
import android.location.GnssAntennaInfo;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.mymap.listener.LandInfoBDSListener;
import com.example.mymap.listener.SearchListener;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import org.json.JSONArray;
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
    public static String qhpkIDCurrent = "";
    public static int numberOfDCCB = 0;
    public static String dccbCurrent = "";
    public static boolean isClickDCCB = false;
    public static JSONArray savedQHPK;
    public static JSONArray savedQHN;
    public static int count_id_layer = 0;
    public static int temp = 0;
    public static String id;
    public static LandInfoBDSListener landInfoBDSListener = null;
    public static SearchListener listener;
    public static String[][] wardName;
    public static String[][] wardId;
    public static String[] districtName;
    public static String[] districtId;
    public static String[] provinceName;
    public static String[] provinceID;
    public static Activity activity= null;
    public static String planningInfo;
    public static View view;
}
