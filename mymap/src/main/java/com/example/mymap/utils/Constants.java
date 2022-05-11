package com.example.mymap.utils;

public class Constants {
    public static final String URL_DIGITAL_LAND_NEW_QQQ = "https://sqhkt-qlqh.tphcm.gov.vn/api/tiles/bandoso/{z}/{x}/{y}";
//    public static final String URL_DIGITAL_LAND_NEW_QQQ = "http://gis.rteknetwork.com:8080/geoserver/gwc/service/wmts?layer=rtek%3Agroup_qhsdd&style=&tilematrixset=EPSG%3A900913&Service=WMTS&Request=GetTile&Version=1.0.0&Format=image%2Fpng&TileMatrix=EPSG%3A900913%3A{z}&TileCol={x}&TileRow={y}";

    public static  String BG_NEN_BAN_DO ;
    public static  String BG_NEN_VE_TINH ;
    public static enum Style
    {
        BG_NEN_BAN_DO, BG_NEN_VE_TINH, FG_TTQH_SO
    }

    public static final String qhpk_crop_url = "https://sqhkt-qlqh.tphcm.gov.vn/api/tiles/qhpk_crop/";
    public static final String dccb_crop_url = "https://sqhkt-qlqh.tphcm.gov.vn/api/tiles/dccb_crop/";
}
