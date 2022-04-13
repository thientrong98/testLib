
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PlanningInfo : Serializable {
    @SerializedName("ThongTinChung")
    var thongTinChung = ""

    @SerializedName("QHPK")
    var qHPK = ""

    @SerializedName("LoGioi")
    var loGioi = ""

    @SerializedName("QHNganh")
    var qHNganh = ""

    @SerializedName("data")
    var data = ""
}