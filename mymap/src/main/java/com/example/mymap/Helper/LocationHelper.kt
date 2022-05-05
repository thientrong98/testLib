
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import org.json.JSONArray
import org.json.JSONException
import kotlin.math.floor

class LocationHelper {
    fun getCenterBounds(coordinates: JSONArray): LatLngBounds? {
        val builder = LatLngBounds.Builder()
        val bounds: LatLngBounds
        var flag = false
        var leng = coordinates.length()
        //cho nay rat phuc tap
        // 1 polygon it nhat la 3 dinh --> it nhat 4 diem
        if (coordinates.length() < 4) {
            flag = true
            try {
                leng = coordinates.getJSONArray(0).length()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        for (i in 0 until leng) {
            try {
                builder.include(
                    LatLng(
                        (if (flag) coordinates.getJSONArray(0)
                            .getJSONArray(i)[1] as Double else coordinates.getJSONArray(i)[1] as Double),
                        (if (flag) coordinates.getJSONArray(0)
                            .getJSONArray(i)[0] as Double else coordinates.getJSONArray(i)[0] as Double)
                    )
                )
            } catch (e: ClassCastException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        bounds = builder.build()
        return bounds
    }

    fun getMarkerPosition(coordinates: JSONArray): LatLng? {
        var position: LatLng = getCenterBounds(coordinates)!!.center
        val x = position.longitude
        val y = position.latitude
        var inside = false
        var i = 0
        var j = coordinates.length() - 1
        while (i < coordinates.length()) {
            try {
                val xi = coordinates.getJSONArray(i)[0] as Double
                val yi = coordinates.getJSONArray(i)[1] as Double
                val xj = coordinates.getJSONArray(j)[0] as Double
                val yj = coordinates.getJSONArray(j)[1] as Double
                val intersect = yi > y != yj > y && x < (xj - xi) * (y - yi) / (yj - yi) + xi
                if (intersect) {
                    inside = !inside
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            j = i++
        }
        if (!inside) {
            val pos = floor(Math.random() * coordinates.length()).toInt()
            try {
                position = LatLng(
                    (coordinates.getJSONArray(pos)[1] as Double),
                    (coordinates.getJSONArray(pos)[0] as Double)
                )
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return position
    }

    fun isLatArrayValid(latArr: DoubleArray): Boolean {
        for (i in latArr.indices) {
            if (latArr[i] < 0) {
                return false
            }
        }
        return true
    }

    fun isLngArrayValid(lngArr: DoubleArray): Boolean {
        for (i in lngArr.indices) {
            if (lngArr[i] < 0) {
                return false
            }
        }
        return true
    }

}