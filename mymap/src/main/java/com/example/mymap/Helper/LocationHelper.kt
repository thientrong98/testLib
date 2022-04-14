
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import org.json.JSONArray
import org.json.JSONException

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
}