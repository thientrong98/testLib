
import com.google.gson.annotations.SerializedName

class QHPKProperties {
    @SerializedName("maqhpksdd")
    var maqhpksdd = ""

    //        return "";
    @SerializedName("maopho")
    var maopho = ""

    @SerializedName("maso")
    var maso = ""

    @SerializedName("chucnang")
    var chucnang = ""

    @SerializedName("rgbcolor")
    var rgbcolor = ""

    @SerializedName("dientich")
    private var dientich = ""

    @SerializedName("tldientich")
    private var tldientich = ""

    @SerializedName("matdo")
    var matdo = ""

    @SerializedName("tangcao")
    var tangcao = ""

    @SerializedName("gid")
    var gid = ""

    @SerializedName("linktable")
    var linktable = ""

    fun getDientich(): String {
        return if (dientich.indexOf('.') + 3 < dientich.length) {
            dientich.substring(0, dientich.indexOf('.') + 3)
        } else dientich
    }

    fun setDientich(dientich: String) {
        this.dientich = dientich
    }

    fun getTldientich(): String {
        return if (tldientich.indexOf('.') + 2 < tldientich.length) {
            tldientich.substring(0, tldientich.indexOf('.') + 2)
        } else tldientich
    }

    fun setTldientich(tldientich: String) {
        this.tldientich = tldientich
    }
}