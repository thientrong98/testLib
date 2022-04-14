import com.google.gson.annotations.SerializedName

class ThongTinChung {
    @SerializedName("mathuadat")
    var mathuadat = ""

    @SerializedName("soto")
    var soto = ""

    @SerializedName("sothua")
    var sothua = ""

    @SerializedName("dientich")
    var dientich = ""

    @SerializedName("tenphuongxa")
    var tenphuongxa = ""

    @SerializedName("tenquanhuyen")
    var tenquanhuyen = ""

    @SerializedName("dsdoan")
    var dsdoan = ArrayList<String>()

    @SerializedName("ranh")
    var ranh = ""

    @SerializedName("coquanpheduyet")
    var coquanpheduyet = ""

    @SerializedName("soquyetdinh")
    var soquyetdinh = ""

    @SerializedName("ngayduyet")
    var ngayduyet = ""

    @SerializedName("dsttdoan")
    val dsttdoan = ArrayList<Dsttdoan>()

    inner class Dsttdoan {
        //
        @SerializedName("qhpkranh_geom")
        val qhpkranh_geom = ""

        @SerializedName("coquanpd")
        var coquanpd = ""

        @SerializedName("tendoan")
        var tendoan = ""

        @SerializedName("ngayduyet")
        var ngayduyet = ""

        @SerializedName("soqd")
        var soqd = ""
    }
}