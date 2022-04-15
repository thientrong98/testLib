package com.example.mymap.utils;

public class Constants {
    public static final String URL_DIGITAL_LAND_NEW_QQQ = "https://sqhkt-qlqh.tphcm.gov.vn/api/tiles/bandoso/{z}/{x}/{y}";
    public static final String URL_RASTER_LAND = "https://sqhkt-qlqh.tphcm.gov.vn/api/tiles/ranh_qhpk_tphcm_v1/{z}/{x}/{y}";
    public static final String URL_RASTER_LAND_SATELLITE = "https://sqhkt-qlqh.tphcm.gov.vn/api/tiles/ranh_qhpk_tphcm_v2/{z}/{x}/{y}";
    public static final String URL_CDN_RASTER = "https://sqhkt-qlqh.tphcm.gov.vn/api/tiles/ranh_caodonen_tphcm_v1/{z}/{x}/{y}";
    public static final String URL_CDN_RASTER_SATELLITE = "https://sqhkt-qlqh.tphcm.gov.vn/api/tiles/ranh_caodonen_tphcm_v2/{z}/{x}/{y}";

    public static  String BG_NEN_BAN_DO ;
    public static  String BG_NEN_VE_TINH ;
    public static enum Style
    {
        BG_NEN_BAN_DO, BG_NEN_VE_TINH, FG_TTQH_GIAY, FG_TTQH_SO, FG_CDN_GIAY, FG_CDN_SO
    }

    public static final String qhpk_crop_url = "https://sqhkt-qlqh.tphcm.gov.vn/api/tiles/qhpk_crop/";
    public static final String qhpk_full_url = "https://sqhkt-qlqh.tphcm.gov.vn/api/tiles/qhpk_full/";
    public static final String dccb_crop_url = "https://sqhkt-qlqh.tphcm.gov.vn/api/tiles/dccb_crop/";
    public static final String dccb_full_url = "https://sqhkt-qlqh.tphcm.gov.vn/api/tiles/dccb_full/";
    public static final String cdn_crop_url = "https://sqhkt-qlqh.tphcm.gov.vn/api/tiles/caodonen_crop/%s/{z}/{x}/{y}";
    public static final String cdn_full_url = "https://sqhkt-qlqh.tphcm.gov.vn/api/tiles/caodonen_full/%s/{z}/{x}/{y}";
}
