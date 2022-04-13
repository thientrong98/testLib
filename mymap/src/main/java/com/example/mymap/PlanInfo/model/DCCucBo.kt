
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DCCucBo(@field:SerializedName("TenDoAn") var tenDoAn: String, var coordinates: Any) :
    Serializable {

    @SerializedName("MaDCCB")
    var maDCCB: String? = null

    @SerializedName("MaQHPK")
    var maQHPK: String? = null

    @SerializedName("STT")
    var sTT: String? = null

    @SerializedName("Ranh")
    var ranh: String? = null

    @SerializedName("MaDCCBRanh")
    var maDCCBRanh: String? = null

    @SerializedName("TenQH")
    var tenQH: String? = null

    @SerializedName("SoQD")
    var soQD: String? = null

    @SerializedName("CoQuanPD")
    var coQuanPD: String? = null

    @SerializedName("NgayDuyet")
    var ngayDuyet: String? = null

}