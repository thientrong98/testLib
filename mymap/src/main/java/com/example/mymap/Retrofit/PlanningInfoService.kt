import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import tech.vlab.ttqhhcm.new_ui.map.models.QHPK

interface PlanningInfoService {
    @FormUrlEncoded
    @POST("/computing/930/api/v3.1/a-z/all")
    fun getPlanningInfoByLandId(@Field("MaThuaDat") ID: String?): Call<PlanningInfo?>?

    @FormUrlEncoded
    @POST("/computing/930/api/v3.1/a-z/all/en")
    fun getPlanningInfoByLandIdEnglish(@Field("MaThuaDat") ID: String?): Call<PlanningInfo?>?

    @FormUrlEncoded
    @POST("/computing/930/api/v3.1/a-z/all")
    fun getPlanningInfoByCoordinate(@Field("ToaDo") coordinate: String?): Call<PlanningInfo?>?

    @FormUrlEncoded
    @POST("/computing/930/api/v3.1/a-z/all/en")
    fun getPlanningInfoByCoordinateEnglish(@Field("ToaDo") coordinate: String?): Call<PlanningInfo?>?

    @FormUrlEncoded
    @POST("/api/doan/ranhqhpk")
    fun getQHPK(@FieldMap params: Map<String, Double>): Call<ArrayList<QHPK?>?>?

    @FormUrlEncoded
    @POST("/api/doan/ranhqhpk/en")
    fun getQHPKEnglish(@FieldMap params: Map<String, Double>): Call<ArrayList<QHPK?>?>?

//    @FormUrlEncoded
//    @POST("/api/doan/caodonen")
//    fun getCaoDoNenInfo(
//        @Field("Lat") lat: Double?,
//        @Field("Lon") lng: Double?
//    ): Call<ArrayList<MaDoAnCDN?>?>?
//
//    @FormUrlEncoded
//    @POST("/api/doan/caodonen/en")
//    fun getCaoDoNenInfoEnglish(
//        @Field("Lat") lat: Double?,
//        @Field("Lon") lng: Double?
//    ): Call<ArrayList<MaDoAnCDN?>?>?
//
//    @GET
//    fun getCaoDoNenSoInfo(@Url url: String?): Call<CaoDoNenSo?>?

    @FormUrlEncoded
    @POST("/computing/930/api/v3.1/a-z/all")
    fun getPlanningInfoByLatLng(
        @Field("Lat") lat: Double?,
        @Field("Lon") lng: Double?
    ): Call<PlanningInfo?>?

    @FormUrlEncoded
    @POST("/computing/930/api/v3.1/a-z/all/en")
    fun getPlanningInfoByLatLngEnglish(
        @Field("Lat") lat: Double?,
        @Field("Lon") lng: Double?
    ): Call<PlanningInfo?>?

    @GET("/api/thua-dat/{id}")
    fun getLandID(@Path("id") id: String?): Call<ResponseBody?>?


    @GET("/api/quanhuyen-phuongxa")
    fun getDistrictWardId(): Call<DistrictWardID>

    @GET("/tinhthanh")
    fun getProvince(): Call<List<Province>>

    @GET("/tinhthanh/{id}")
    fun getDistrict(@Path("id") id: String?): Call<Province>

    @FormUrlEncoded
    @POST("https://sqhkt-qlqh.tphcm.gov.vn/api/coordinates/tovn2000")
    fun getCoordinate(@Field("coordinates") coordinates: String?): Call<ResponseBody?>?

//    @GET("https://maps.vietmap.vn/api/search")
//    fun getStreet(
//        @Query(value = "text") text: String?,
//        @Query(value = "size") size: String?,
//        @Query(value = "apikey") apikey: String?,
//        @Query(value = "focus.point.lat") lat: String?,
//        @Query(value = "focus.point.lon") lon: String?
//    ): Call<Street?>?

    @FormUrlEncoded
    @POST("/api/quy-hoach-phan-khu/{id}/tai-ve")
    fun sendMailQHPK(@Field("email") email: String?, @Path("id") id: String?): Call<ResponseBody?>?

    @FormUrlEncoded
    @POST("/api/dieu-chinh-cuc-bo/{id}/tai-ve")
    fun sendMailDCCB(@Field("email") email: String?, @Path("id") id: String?): Call<ResponseBody?>?

    @FormUrlEncoded
    @POST("/api/get-share-link")
    fun getShareLink(
        @Field("coordinate") coordinate: String?,
        @Field("type") type: String?
    ): Call<ResponseBody?>?

//    @GET("/api/get-share-coordinate")
//    fun getShareCoordinate(@Query(value = "share") share: String?): Call<ShareLink?>?

    @FormUrlEncoded
    @POST("/api/store-uuid")
    fun sendUuid(@Field("uuid") uuid: String?): Call<ResponseBody?>?

    @GET("/api/check-uuid")
    fun checkUuid(@Query(value = "uuid") uuid: String?): Call<ResponseBody?>?
}