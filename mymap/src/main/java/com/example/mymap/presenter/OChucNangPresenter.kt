
import android.annotation.SuppressLint
import androidx.annotation.NonNull
import com.example.mymap.Retrofit.OChucNangInfoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OChucNangPresenter(landInfoFragment: LandInfoFragment) {
    private val listener: LoadOChucNangListener = landInfoFragment

    fun getOChucNang930(ID: String?) {
        val oChucNangCall: Call<OChucNang> = ApiHelper().getOChucNang930Service().getOChucNang(ID)

        oChucNangCall.enqueue(object : Callback<OChucNang?> {
            override fun onResponse(
                @NonNull call: Call<OChucNang?>,
                @NonNull response: Response<OChucNang?>
            ) {
                if (response.body() != null && response.code() == 200) {
                    listener.onLoadOChucNangSucess(response.body())
                } else {
                    listener.onPopup()
                }
            }

            override fun onFailure(@NonNull call: Call<OChucNang?>, @NonNull t: Throwable) {
                listener.onPopup()
            }
        })
    }

    fun getChiTieuHonHop(ID: String?) {
        val service: OChucNangInfoService = ApiHelper().getOChucNang930Service()
        val ocn: Call<List<ChiTieuHonHop>> = service.getChiTieuHonHop(ID)

        ocn.enqueue(object : Callback<List<ChiTieuHonHop>> {
            @SuppressLint("LogNotTimber")
            override fun onResponse(
                @NonNull call: Call<List<ChiTieuHonHop>>,
                @NonNull response: Response<List<ChiTieuHonHop>>
            ) {
                val chiTieuHonHops: List<ChiTieuHonHop?>? = response.body()
                if (response.body() != null && response.code() == 200) {
                    listener.onLoadChiTieuHonHopSucess(chiTieuHonHops)
                } else {
                    listener.onLoadChiTieuHonHopFailure("Gặp lỗi khi lấy thông tin. Vui lòng thử lại.")
                }
            }

            override fun onFailure(
                @NonNull call: Call<List<ChiTieuHonHop>>,
                @NonNull t: Throwable
            ) {
                listener.onLoadChiTieuHonHopFailure("Gặp lỗi khi lấy thông tin. Vui lòng thử lại.")
            }
        })
    }

    interface LoadOChucNangListener {
        fun onLoadOChucNangSucess(oChucNang: OChucNang?)
        fun onLoadOChucNangFailure(string: String?)
        fun onLoadChiTieuHonHopSucess(chiTieuHonHops: List<ChiTieuHonHop?>?)
        fun onLoadChiTieuHonHopFailure(string: String?)
        fun onPopup()
    }

}