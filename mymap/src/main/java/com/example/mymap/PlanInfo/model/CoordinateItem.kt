
import android.text.Editable

class CoordinateItem {
    private var index = 0
    private var coordinateX: String? = null
    private var coordinateY: String? = null
    var edtX: Editable? = null
    var edtY: Editable? = null
    var isHasRemoved = false

    constructor(index: Int, coordinateX: String?, coordinateY: String?) {
        this.index = index
        this.coordinateX = coordinateX
        this.coordinateY = coordinateY
    }

    constructor() {}

    fun getCoordinateX(): String? {
        return coordinateX
    }

    fun setCoordinateX(coordinateX: String?) {
        this.coordinateX = coordinateX
    }

    fun getCoordinateY(): String? {
        return coordinateY
    }

    fun setCoordinateY(coordinateY: String?) {
        this.coordinateY = coordinateY
    }

    fun setIndex(index: Int) {
        this.index = index
    }

    companion object {
        const val index = 0
    }
}