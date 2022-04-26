
import ChangeLayer
import DemoFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.BoundingBox
import com.mapbox.mapboxsdk.geometry.LatLng

class FragmentManager1{
    companion object {
//        fun newInstance(
////            center: LatLng?,
////            bb: BoundingBox?,
////            zoom: Double?,
////            minZoom: Double?,
////            maxZoom: Double?,
//            fgMapFirst: String,
//            bgMapFirst: String,
//            tileBaseMap: String?,
//            tileSatellite: String?
//        ): DemoFragment {
//            val data = Bundle()
//            data.putString("fgMapFirst", fgMapFirst)
//            data.putString("bgMapFirst", bgMapFirst)
//            data.putString("tileBaseMap", tileBaseMap)
//            data.putString("tileSatellite", tileSatellite)
//
//            return DemoFragment().apply {
//                arguments = data
//            }
//        }

        fun showFragment(
            activity: AppCompatActivity,
            center: LatLng?,
            bb: BoundingBox?,
            zoom: Double?,
            maxZoom: Double?,
            minZoom: Double?,
            fgMapFirst: String,
            bgMapFirst: String,
            tileBaseMap: String,
            tileSatellite: String,
            location: LatLng
        ) {
            if (activity.supportFragmentManager.findFragmentById(android.R.id.content) == null) {
                activity.supportFragmentManager.beginTransaction()
                    .add(android.R.id.content, DemoFragment( ))
                    .addToBackStack(null)
                    .commit()
            }
        }

        fun changeBGMap(name: String){
          ChangeLayer().changeMapBackground(name,null)
        }

        fun changeFGMap(name: String) {
            ChangeLayer().changeMapForeground(name,null)
            }

        }
}