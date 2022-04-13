
import com.google.gson.annotations.SerializedName

class Geometry {
    @SerializedName("type")
    var type: String? = null

    @SerializedName("coordinates")
    var coordinates: ArrayList<*>? = null
}