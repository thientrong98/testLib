import com.example.mymap.Converter.SevenThamSoVN2000_WGS84
import com.example.mymap.Converter.SevenThamSoWGS84_VN2000
import com.example.mymap.Converter.XL_Goc
import tech.vlab.ttqhhcm.Converter.Matrix_LT2

object XYZ_2_XYZ {
    fun VN2000_2_WGS84(X1: Double, Y1: Double, Z1: Double): DoubleArray {
        val Wx: Double = SevenThamSoVN2000_WGS84.Wx * 4.84813681109536E-6
        val Wy: Double = SevenThamSoVN2000_WGS84.Wy * 4.84813681109536E-6
        val Wz: Double = SevenThamSoVN2000_WGS84.Wz * 4.84813681109536E-6
        val XYZ_1 = doubleArrayOf(X1, Y1, Z1)
        val r02 = -Wy
        val r10 = -Wz
        val r11 = 1.000000252906278
        val r21 = -Wx
        val r22 = 1.000000252906278
        val R = arrayOfNulls<DoubleArray>(3)
        R[0] = doubleArrayOf(1.000000252906278, Wz, r02)
        R[1] = doubleArrayOf(r10, r11, Wx)
        R[2] = doubleArrayOf(Wy, r21, r22)
        return Matrix_LT2.multiply_Vector(
            Matrix_LT2.inverse(Matrix_LT2(R )),
            Matrix_LT2.add_Vector(
                XYZ_1,
                doubleArrayOf(
                    -SevenThamSoVN2000_WGS84.Delta_X0,
                    -SevenThamSoVN2000_WGS84.Delta_Y0,
                    -SevenThamSoVN2000_WGS84.Delta_Z0
                )
            )
        )
    }


    fun WGS84_2_VN2000(X1: Double, Y1: Double, Z1: Double): DoubleArray {
        val Wx: Double = SevenThamSoWGS84_VN2000.Wx * 4.84813681109536E-6
        val Wy: Double = SevenThamSoWGS84_VN2000.Wy * 4.84813681109536E-6
        val Wz: Double = SevenThamSoWGS84_VN2000.Wz * 4.84813681109536E-6
        val XYZ_1 = doubleArrayOf(X1, Y1, Z1)
        val r02 = -Wy
        val r10 = -Wz
        val r11 = 1.000000252906278
        val r21 = -Wx
        val r22 = 1.000000252906278
        val R = arrayOfNulls<DoubleArray>(3)
        R[0] = doubleArrayOf(1.000000252906278, Wz, r02)
        R[1] = doubleArrayOf(r10, r11, Wx)
        R[2] = doubleArrayOf(Wy, r21, r22)
        return Matrix_LT2.multiply_Vector(
            Matrix_LT2.inverse(Matrix_LT2(R)),
            Matrix_LT2.add_Vector(
                XYZ_1,
                doubleArrayOf(
                    -SevenThamSoWGS84_VN2000.Delta_X0,
                    -SevenThamSoWGS84_VN2000.Delta_Y0,
                    -SevenThamSoWGS84_VN2000.Delta_Z0
                )
            )
        )
    }

    fun VN2000_2_WGS84_second(X1: Double, Y1: Double, Z1: Double): DoubleArray {
        val Wx: Double = SevenThamSoVN2000_WGS84.Wx * 4.84813681109536E-6
        val Wy: Double = SevenThamSoVN2000_WGS84.Wy * 4.84813681109536E-6
        val Wz: Double = SevenThamSoVN2000_WGS84.Wz * 4.84813681109536E-6
        val X2: Double =
            SevenThamSoVN2000_WGS84.Delta_X0 + (Wz * Y1 + X1 - Wy * Z1) * 1.000000252906278
        val Y2: Double =
            SevenThamSoVN2000_WGS84.Delta_Y0 + (-Wz * X1 + Y1 + Wx * Z1) * 1.000000252906278
        val Z2: Double =
            SevenThamSoVN2000_WGS84.Delta_Z0 + (Wy * X1 - Wx * Y1 + Z1) * 1.000000252906278
        return doubleArrayOf(X2, Y2, Z2)
    }

    fun WGS84_2_VN2000_second(X1: Double, Y1: Double, Z1: Double): DoubleArray {
        val Wx: Double = SevenThamSoWGS84_VN2000.Wx * 4.84813681109536E-6
        val Wy: Double = SevenThamSoWGS84_VN2000.Wy * 4.84813681109536E-6
        val Wz: Double = SevenThamSoWGS84_VN2000.Wz * 4.84813681109536E-6
        val X2: Double =
            SevenThamSoWGS84_VN2000.Delta_X0 + (Wz * Y1 + X1 - Wy * Z1) * 1.000000252906278
        val Y2: Double =
            SevenThamSoWGS84_VN2000.Delta_Y0 + (-Wz * X1 + Y1 + Wx * Z1) * 1.000000252906278
        val Z2: Double =
            SevenThamSoWGS84_VN2000.Delta_Z0 + (Wy * X1 - Wx * Y1 + Z1) * 1.000000252906278
        return doubleArrayOf(X2, Y2, Z2)
    }

    object BL2BL {
        fun BLH_vn2000_2_wgs84(B: Double, L: Double, H: Double): DoubleArray {
            val XYZ = BLH_2_XYZ.BLH2XYZ(B, L, H)
            val XYZ84 = VN2000_2_WGS84_second(XYZ[0], XYZ[1], XYZ[2])
            val BLH84 = BLH_2_XYZ.XYZ2BLH(XYZ84[0], XYZ84[1], XYZ84[2])
            BLH84[0] = XL_Goc().goc2ddmmss(BLH84[0] * 180.0 / 3.141592653589793)
            BLH84[1] = XL_Goc().goc2ddmmss(BLH84[1] * 180.0 / 3.141592653589793)
            return BLH84
        }

        fun BLH_WGS84_2_VN2000(B: Double, L: Double, H: Double): DoubleArray {
            val XYZ = BLH_2_XYZ.BLH2XYZ(B, L, H)
            val XYZvn = WGS84_2_VN2000_second(XYZ[0], XYZ[1], XYZ[2])
            val BLHvn = BLH_2_XYZ.XYZ2BLH(XYZvn[0], XYZvn[1], XYZvn[2])
            BLHvn[0] = XL_Goc().goc2ddmmss(BLHvn[0] * 180.0 / 3.141592653589793)
            BLHvn[1] = XL_Goc().goc2ddmmss(BLHvn[1] * 180.0 / 3.141592653589793)
            return BLHvn
        }
    }

    object BLH_2_XYZ {
        fun BLH2XYZ(B: Double, L: Double, H: Double): DoubleArray {
            var B = B
            var L = L
            B = XY_BL_UTM.Degree_2_Rad(B)
            L = XY_BL_UTM.Degree_2_Rad(L)
            val N = 6378137.0 / Math.sqrt(1.0 - Math.sin(B) * WGS84_Ellipsoid.e2 * Math.sin(B))
            val X = (N + H) * Math.cos(B) * Math.cos(L)
            val Y = (N + H) * Math.cos(B) * Math.sin(L)
            val Z = ((1.0 - WGS84_Ellipsoid.e2) * N + H) * Math.sin(B)
            return doubleArrayOf(X, Y, Z)
        }

        fun XYZ2BLH(X: Double, Y: Double, Z: Double): DoubleArray {
            var L = Math.atan(Y / X)
            if (L < 0.0) {
                L += 3.141592653589793
            }
            val R = Math.sqrt(X * X + Y * Y)
            val Q = Math.atan(Z * 6378137.0 / (R * WGS84_Ellipsoid.f8b))
            val B = Math.atan(
                (Z + WGS84_Ellipsoid.e2f * WGS84_Ellipsoid.f8b * Math.pow(
                    Math.sin(Q),
                    3.0
                )) / (R - WGS84_Ellipsoid.e2 * 6378137.0 * Math.pow(
                    Math.cos(Q), 3.0
                ))
            )
            val H = (R - Math.cos(B) * (6378137.0 / Math.sqrt(
                1.0 - Math.sin(B) * WGS84_Ellipsoid.e2 * Math.sin(B)
            ))) / Math.cos(B)
            return doubleArrayOf(B, L, H)
        }
    }
}