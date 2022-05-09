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

    //    @SerializedName("qhsdd"   ) var qhsdd   : Qhsdd?             = Qhsdd()
//    @SerializedName("qhpksdd" ) var qhpksdd : Qhpksdd?           = Qhpksdd()
    @SerializedName("thuadat")
    var thuadat: ArrayList<Thuadat> = arrayListOf()
}

class Thuadat(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("geometry") var geometry: Geometry? = Geometry(),
    @SerializedName("properties") var thongTinChung: Properties? = Properties()
)

class Properties(
    @SerializedName("soto") var soto: String ="",
    @SerializedName("sonha") var sonha: String ="",
    @SerializedName("sothua") var sothua: String = "",
    @SerializedName("dientich") var dientich: String = "",
    @SerializedName("objectid") var objectid: String = "",
    @SerializedName("phuongxa") var phuongxa: String = "",
    @SerializedName("tenduong") var tenduong: String = "",
    @SerializedName("mathuadat") var mathuadat: String = "",
    @SerializedName("quanhuyen") var quanhuyen: String ="",
    @SerializedName("tinhthanh") var tinhthanh: String = "",
    @SerializedName("shape_area") var shapeArea: String = "",
    @SerializedName("shape_length") var shapeLength: String = "",

)