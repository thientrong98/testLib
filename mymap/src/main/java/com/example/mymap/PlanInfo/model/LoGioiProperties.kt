
import com.google.gson.annotations.SerializedName

class LoGioiProperties {
    @SerializedName("tenduong")
    var tenduong = ""

    @SerializedName("logioi")
    var logioi = ""
        get() = java.lang.Double.valueOf(field).toString()

    @SerializedName("tenhem")
    var tenhem = ""
}