
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DistrictWardID : Serializable {
    @SerializedName("data")
    var data: List<Datum>? = null

    inner class Datum {
        @SerializedName("stt")
        var stt = ""

        @SerializedName("maquanhuyen")
        var maquanhuyen = ""

        @SerializedName("tenquanhuyen")
        var tenquanhuyen = ""

        @SerializedName("caphanhchinh")
        var caphanhchinh = ""

        @SerializedName("phuongxa")
        var phuongxa: List<DatumWard>? = null
    }

    inner class DatumWard {
        @SerializedName("stt")
        var stt = ""

        @SerializedName("maphuongxa")
        var maphuongxa = ""

        @SerializedName("tenphuongxa")
        var tenphuongxa = ""

        @SerializedName("caphanhchinh")
        var caphanhchinh = ""

        @SerializedName("maquanhuyen")
        var maquanhuyen = ""
    }
}