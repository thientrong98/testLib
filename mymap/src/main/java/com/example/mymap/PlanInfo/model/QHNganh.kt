
import Geometry
import com.google.gson.annotations.SerializedName

class QHNganh {
    @SerializedName("geometry")
    var geometry: Geometry? = null

    @SerializedName("properties")
    var properties: QHNProperties? = null

    inner class QHNProperties {
        @SerializedName("tenduan")
        var tenduan = ""

        @SerializedName("chucnang")
        var chucnang = ""

        @SerializedName("dientich")
        var dientich = ""

        @SerializedName("tldientich")
        var tldientich = ""

        @SerializedName("phannhom")
        var phannhom = ""

        @SerializedName("vitri")
        var vitri = ""

        @SerializedName("tencdt")
        var tencdt = ""

        @SerializedName("coquanpd")
        var coquanpd = ""

        @SerializedName("soqd")
        var soqd = ""

        @SerializedName("ngayduyet")
        var ngayduyet = ""

        @SerializedName("ranhlayer")
        var ranhlayer = ""

        @SerializedName("stt")
        var stt = ""
    }
}