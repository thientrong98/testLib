import com.example.mymap.Retrofit.OChucNangInfoService

class ApiHelper {
    private val API_HCM_URL_NEW: String = "https://thongtinquyhoach.hochiminhcity.gov.vn/"
    private val API_HCM_URL: String = "https://sqhkt-qlqh.tphcm.gov.vn/"
    private val API_COMPUTING_URL: String = "https://sqhkt-qlqh.tphcm.gov.vn"
    private val API_930_URL: String = "https://sqhkt-qlqh.tphcm.gov.vn"
    private val API_930_URL_NEW: String = "https://thongtinquyhoach.hochiminhcity.gov.vn"
    private val OCR_URL: String = "http://api.mmlab.uit.edu.vn"
private val API_PROVINCE: String ="https://gis.rteknetwork.com"

    fun getRasterPlanningInfoService(): PlanningInfoService? {
        return RetrofitClient.getClient(API_COMPUTING_URL)
            .create(
                PlanningInfoService::class.java
            )
    }

    fun getPlanningInfoService(): PlanningInfoService? {
        return RetrofitClient.getClient(API_HCM_URL)
            .create(
                PlanningInfoService::class.java
            )
    }

    fun getInfoProvinceService(): PlanningInfoService? {
        return RetrofitClient.getClient(API_PROVINCE)
            .create(
                PlanningInfoService::class.java
            )
    }

    fun getOChucNang930Service(): OChucNangInfoService {
        return RetrofitClient.getClient(API_930_URL)
            .create(
                OChucNangInfoService::class.java
            )
    }



}