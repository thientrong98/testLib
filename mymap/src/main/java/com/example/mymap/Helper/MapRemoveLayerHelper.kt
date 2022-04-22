
import com.example.mymap.utils.GlobalVariables
import com.mapbox.mapboxsdk.maps.MapboxMap
import org.json.JSONArray

class MapRemoveLayerHelper {
     fun removeOChucNangLayer() {
        if(GlobalVariables.mMap != null){
            GlobalVariables.mMap.getStyle {
                if (it.getLayer("ochucnang-layer") != null) {
                    it.removeLayer("ochucnang-layer")
                }
                if (it.getLayer("ochucnang-stroke") != null) {
                    it.removeLayer("ochucnang-stroke")
                }
                if (it.getSource("ochucnang-source") != null) {
                    it.removeSource("ochucnang-source")
                }
            }
        }
    }

    fun removeSketchLayers(map: MapboxMap) {
        map.getStyle {
            if (it.getLayer("sketch-layer") != null) {
                it.removeLayer("sketch-layer")
            }
            if (it.getSource("sketch-source") != null) {
                it.removeSource("sketch-source")
            }
        }

    }

     fun removeRasterLayer(map: MapboxMap, dccbId: String, qhpkId: String) {
        map.getStyle {
            if (it.getLayer("$dccbId-dccb-layer") != null) {
                it.removeLayer("$dccbId-dccb-layer")
            }
            if (it.getLayer("$qhpkId-qhpk-layer") != null) {
                it.removeLayer("$qhpkId-qhpk-layer")
            }
            if (it.getSource("$dccbId-dccb-source") != null) {
                it.removeSource("$dccbId-dccb-source")
            }
            if (it.getSource("$qhpkId-qhpk-source") != null) {
                it.removeSource("$qhpkId-qhpk-source")
            }
        }

    }

    fun removeFullRasterLayer(map: MapboxMap, dccbId: String, qhpkId: String) {
        map.getStyle {
            if (it.getLayer("$dccbId-full-dccb-layer") != null) {
                it.removeLayer("$dccbId-full-dccb-layer")
            }
            if (it.getLayer("$qhpkId-full-qhpk-layer") != null) {
                it.removeLayer("$qhpkId-full-qhpk-layer")
            }
            if (it.getSource("$dccbId-full-dccb-source") != null) {
                it.removeSource("$dccbId-full-dccb-source")
            }
            if (it.getSource("$qhpkId-full-qhpk-source") != null) {
                it.removeSource("$qhpkId-full-qhpk-source")
            }
        }

    }

    fun removeSourcesAndLayers(map: MapboxMap, dccbId: String, qhpkId: String, QHPKs: JSONArray?) {
        map.removeAnnotations()
        map.getStyle {
            if (QHPKs != null) {
                for (i in 0 until QHPKs.length()) {
                    if (it.getLayer("qhpksdd-stroke-" + (i + 1).toString()) != null) {
                        it.removeLayer("qhpksdd-stroke-" + (i + 1).toString())
                    }
                    if (it.getLayer("qhpksdd-layer-" + (i + 1).toString()) != null) {
                        it.removeLayer("qhpksdd-layer-" + (i + 1).toString())
                    }
                    if (it.getSource("qhpksdd-source-" + (i + 1).toString()) != null) {
                        it.removeSource("qhpksdd-source-" + (i + 1).toString())
                    }
                }
            }
        }

        removeOChucNangLayer()
        removeRasterLayer(map, dccbId, qhpkId)
        removeFullRasterLayer(map, dccbId, qhpkId)
    }
}