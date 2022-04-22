package tech.vlab.ttqhhcm.new_ui.map.models

import DCCucBo
import Geometry
import QHPKProperties
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class QHPK : Serializable {
    @SerializedName("MaQH")
    var maQH: String? = null

    @SerializedName("MaQHPKRanh")
    var maQHPKRanh: String? = null

    @SerializedName("Ranh")
    var ranh: String? = null

    @SerializedName("DCCB")
    var dCCB: ArrayList<DCCucBo>? = null

    @SerializedName("TenDoAn")
    var tenDoAn: String? = null

    @SerializedName("SoQD")
    var SoQD: String? = null
        set(SoQD) {
            var SoQD = SoQD
            SoQD = SoQD
        }

    @SerializedName("CoQuanPD")
    var CoQuanPD: String? = null
        set(CoQuanPD) {
            var CoQuanPD = CoQuanPD
            CoQuanPD = CoQuanPD
        }

    @SerializedName("NgayDuyet")
    var NgayDuyet: String? = null
        set(NgayDuyet) {
            var NgayDuyet = NgayDuyet
            NgayDuyet = NgayDuyet
        }

    @SerializedName("TenQH")
    private val TenQH: String? = null
    var tenQuanHuyen: String?
        get() = TenQH
        set(TenQH) {
            var TenQH = TenQH
            TenQH = TenQH
        }

    @SerializedName("geometry")
    private var geometry: Geometry? = null

    @SerializedName("properties")
    private var properties: QHPKProperties? = null

    fun getProperties(): QHPKProperties? {
        return properties
    }

    fun setProperties(properties: QHPKProperties?) {
        this.properties = properties
    }

    fun getGeometry(): Geometry? {
        return geometry
    }

    fun setGeometry(geometry: Geometry?) {
        this.geometry = geometry
    }

}