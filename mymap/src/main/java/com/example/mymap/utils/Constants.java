package com.example.mymap.utils;

public class Constants {
    public static final String URL_DIGITAL_LAND = "https://thongtinquyhoach.hochiminhcity.gov.vn/api/tiles/vector/{z}/{x}/{y}";
    //link ranh giữa các miếng đất
    public static final String URL_DIGITAL_LAND_NEW = "https://gis.vlab.tech:8443/geoserver/gwc/service/wmts?layer=thongtinquyhoach_tphcm%3Athongtinquyhoach_tphcm_thuadat&style=&tilematrixset=EPSG%3A900913&Service=WMTS&Request=GetTile&Version=1.0.0&Format=image%2Fpng&TileMatrix=EPSG%3A900913%3A{z}&TileCol={x}&TileRow={y}";
    public static final String URL_DIGITAL_LAND_NEW_NEW = "https://gis.vlab.tech/api/tiles/ttqh_tphcm/{z}/{x}/{y}";

    public static final String URL_DIGITAL_LAND_NEW_QQQ = "https://sqhkt-qlqh.tphcm.gov.vn/api/tiles/bandoso/{z}/{x}/{y}";

    public static final String URL_RASTER_LAND = "https://sqhkt-qlqh.tphcm.gov.vn/api/tiles/ranh_qhpk_tphcm_v1/{z}/{x}/{y}";

    public static final String URL_RASTER_LAND_SATELLITE = "https://sqhkt-qlqh.tphcm.gov.vn/api/tiles/ranh_qhpk_tphcm_v2/{z}/{x}/{y}";

    public static final String URL_CDN_RASTER = "https://sqhkt-qlqh.tphcm.gov.vn/api/tiles/ranh_caodonen_tphcm_v1/{z}/{x}/{y}";

    public static final String URL_CDN_RASTER_SATELLITE = "https://sqhkt-qlqh.tphcm.gov.vn/api/tiles/ranh_caodonen_tphcm_v2/{z}/{x}/{y}";

    public static final String URL_CDN_DIGITAL = "https://gis.vlab.tech:8443/geoserver/gwc/service/wmts" +
            "?layer=caodo%3Acao_do_16_quan&style&tilematrixset=EPSG%3A900913&Service=WMTS&Request=GetTile" +
            "&Version=1.0.0&Format=image%2Fpng&TileMatrix=EPSG%3A900913%3A{z}&TileCol={x}&TileRow={y}";

    public static final String BG_NEN_BAN_DO = "mapbox://styles/ttqhhcm/cjkt24uy402wg2sqqzopgctxx";
    public static final String BG_NEN_VE_TINH = "mapbox://styles/ttqhhcm/cjldd4sr1335c2srqbbr8lgko";
    public static enum Style
    {
        //BG----> Background
        //FG ---> foreground
        BG_NEN_BAN_DO, BG_NEN_VE_TINH, FG_TTQH_GIAY, FG_TTQH_SO, FG_CDN_GIAY, FG_CDN_SO
    }

    public static final String qhpk_crop_url = "https://sqhkt-qlqh.tphcm.gov.vn/api/tiles/qhpk_crop/";
    public static final String qhpk_full_url = "https://sqhkt-qlqh.tphcm.gov.vn/api/tiles/qhpk_full/";
    public static final String dccb_crop_url = "https://sqhkt-qlqh.tphcm.gov.vn/api/tiles/dccb_crop/";
    public static final String dccb_full_url = "https://sqhkt-qlqh.tphcm.gov.vn/api/tiles/dccb_full/";
    public static final String cdn_crop_url = "https://sqhkt-qlqh.tphcm.gov.vn/api/tiles/caodonen_crop/%s/{z}/{x}/{y}";
    public static final String cdn_full_url = "https://sqhkt-qlqh.tphcm.gov.vn/api/tiles/caodonen_full/%s/{z}/{x}/{y}";
    public static String CAODONEN_API_GET = "https://gis.vlab.tech:8443/geoserver/caodo/wms?"
            + "SERVICE=WMS"
            + "&VERSION=1.1.1"
            + "&REQUEST=GetFeatureInfo"
            + "&FORMAT=image/jpeg"
            + "&TRANSPARENT=true"
            + "&QUERY_LAYERS=caodo:ranh_caodonen_104doan_v1"
            + "&STYLES"
            + "&LAYERS=caodo:ranh_caodonen_104doan_v1"
            + "&INFO_FORMAT=application/json"
            + "&FEATURE_COUNT=50"
            + "&X=%d"
            + "&Y=%d"
            + "&SRS=EPSG:4326"
            + "&WIDTH=%d"
            + "&HEIGHT=%d"
            + "&BBOX=%s";

    public static String CAODONEN_SO_API_GET = "https://gis.vlab.tech:8443/geoserver/caodo/wms?"
            + "SERVICE=WMS"
            + "&VERSION=1.1.1"
            + "&REQUEST=GetFeatureInfo"
            + "&FORMAT=image/jpeg"
            + "&TRANSPARENT=true"
            + "&QUERY_LAYERS=caodo:cao_do_16_quan"
            + "&STYLES"
            + "&LAYERS=caodo:cao_do_16_quan"
            + "&INFO_FORMAT=application/json"
            + "&FEATURE_COUNT=50"
            + "&X=%d"
            + "&Y=%d"
            + "&SRS=EPSG:4326"
            + "&WIDTH=%d"
            + "&HEIGHT=%d"
            + "&BBOX=%s";

    //TODO Thay doi link
    public static String DIEM_CAO_DO_GET = "https://gis.vlab.tech:8443/geoserver/caodo/wfs?" +
            "SERVICE=WFS" +
            "&VERSION=1.1.1" +
            "&REQUEST=GetFeature" +
            "&FORMAT=image/jpeg" +
            "&TRANSPARENT=true" +
            "&TYPENAMES=caodo:diemcaodo13quan"+
            "&OUTPUTFORMAT=application/json";
}
