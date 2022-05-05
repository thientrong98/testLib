import kotlin.math.*

object XY_BL_UTM {
    fun Degree_2_Rad(B: Double): Double {
        val pdo = (B / 10000.0).toInt()
        val tam = B - (pdo * 10000).toDouble()
        val pph = (tam / 100.0).toInt()
        return 3.141592653589793 * (pph.toDouble() / 60.0 + pdo.toDouble() + (tam - (pph * 100) as Double) / 3600.0) / 180.0
    }

    fun Rad_2_Degrees(rad: Double): DoubleArray {
        val mdo = 180.0 * rad / 3.141592653589793
        val p1 = (mdo - mdo.toInt().toDouble()) * 60.0
        val giay = (p1 - p1.toInt().toDouble()) * 60.0
        return doubleArrayOf(mdo.toInt().toDouble(), p1.toInt().toDouble(), giay)
    }

    fun BL2XY(B: Double, L: Double, KTtruc: Double, zone: Double): DoubleArray {
        var B = B
        var L = L
        var KTtruc = KTtruc
        val k: Double
        B = Degree_2_Rad(B)
        L = Degree_2_Rad(L)
        KTtruc = Degree_2_Rad(KTtruc)
        val m0: Double = 6378137.0 * (1.0 - WGS84_Ellipsoid.e2)
        val m2: Double = 3.0 * WGS84_Ellipsoid.e2 * m0 / 2.0
        val m4: Double = 5.0 * WGS84_Ellipsoid.e2 * m2 / 4.0
        val m6: Double = 7.0 * WGS84_Ellipsoid.e2 * m4 / 6.0
        val m8: Double = 9.0 * WGS84_Ellipsoid.e2 * m6 / 8.0
        val X0 =
            (m2 / 2.0 + m0 + 3.0 * m4 / 8.0 + 5.0 * m6 / 16.0 + 35.0 * m8 / 128.0) * B - Math.sin(
                2.0 * B
            ) * (m2 / 2.0 + m4 / 2.0 + 15.0 * m6 / 32.0 + 7.0 * m8 / 16.0) / 2.0 + Math.sin(4.0 * B) * (m4 / 8.0 + 3.0 * m6 / 16.0 + 7.0 * m8 / 32.0) / 4.0 - Math.sin(
                6.0 * B
            ) * (m6 / 32.0 + m8 / 16.0) / 6.0 + sin(8.0 * B) * (m8 / 128.0) / 8.0
        val si = sin(B)
        val N = 6378137.0 / sqrt(1.0 - WGS84_Ellipsoid.e2 * si * si)
        val nuy = sqrt(WGS84_Ellipsoid.e2f) * cos(B)
        val nuy2 = nuy * nuy
        val nuy4 = nuy2 * nuy2
        val t = tan(B)
        val t2 = t * t
        val t4 = t.pow(4.0)
        val t6 = t.pow(6.0)
        val co = cos(B)
        val co3 = co.pow(3.0)
        val co5 = co.pow(5.0)
        val co7 = co.pow(7.0)
        val A2 = N * si * co / 2.0
        val A4 = N * si * co3 * (5.0 - t2 + 9.0 * nuy2 + 4.0 * nuy4) / 24.0
        val A6 = N * si * co5 * (61.0 - 58.0 * t2 + t4 + 270.0 * nuy2 - 330.0 * nuy2 * t2) / 720.0
        val A8 = N * si * co7 * (1385.0 - 311.0 * t2 + 543.0 * t4 - t6) / 40320.0
        val B1 = N * co
        val B3 = N * co3 * (1.0 - t2 + nuy2) / 6.0
        val B5 = N * co5 * (5.0 - 18.0 * t2 + t4 + 14.0 * nuy2 - 58.0 * nuy2 * t2) / 120.0
        val B7 = N * co7 * (61.0 - 479.0 * t2 + 179.0 * t4 - t6) / 5040.0
        k = if (zone == 6.0) {
            0.9996
        } else {
            0.9999
        }
        val l = L - KTtruc
        val xG =
            A2 * l * l + X0 + Math.pow(l, 4.0) * A4 + Math.pow(l, 6.0) * A6 + Math.pow(l, 8.0) * A8
        val y = (B1 * l + Math.pow(l, 3.0) * B3 + Math.pow(l, 5.0) * B5 + Math.pow(
            l,
            7.0
        ) * B7) * k + 500000.0
        return doubleArrayOf(xG * k, y)
    }

    fun XY2BL(X: Double, Y: Double, KTtruc: Double, zone: Double): DoubleArray {
        var Y = Y
        var KTtruc = KTtruc
        val k: Double
        KTtruc = Degree_2_Rad(KTtruc)
        k = if (zone == 3.0) {
            0.9999
        } else {
            0.9996
        }
        Y = (Y - 500000.0) / k
        val m0: Double = 6378137.0 * (1.0 - WGS84_Ellipsoid.e2)
        val m2: Double = 3.0 * WGS84_Ellipsoid.e2 * m0 / 2.0
        val m4: Double = 5.0 * WGS84_Ellipsoid.e2 * m2 / 4.0
        val m6: Double = 7.0 * WGS84_Ellipsoid.e2 * m4 / 6.0
        val m8: Double = 9.0 * WGS84_Ellipsoid.e2 * m6 / 8.0
        val a0 = m2 / 2.0 + m0 + 3.0 * m4 / 8.0 + 5.0 * m6 / 16.0 + 35.0 * m8 / 128.0
        val a2 = m2 / 2.0 + m4 / 2.0 + 15.0 * m6 / 32.0 + 7.0 * m8 / 16.0
        val a4 = m4 / 8.0 + 3.0 * m6 / 16.0 + 7.0 * m8 / 32.0
        val a6 = m6 / 32.0 + m8 / 16.0
        val a8 = m8 / 128.0
        val t0 = X / k / a0
        var t11 =
            t0 + (Math.sin(2.0 * t0) * a2 / 2.0 - Math.sin(4.0 * t0) * a4 / 4.0 + Math.sin(6.0 * t0) * a6 / 6.0 - Math.sin(
                8.0 * t0
            ) * a8 / 8.0) / a0
        var t22 =
            t0 + (Math.sin(2.0 * t11) * a2 / 2.0 - Math.sin(4.0 * t11) * a4 / 4.0 + Math.sin(6.0 * t11) * a6 / 6.0 - Math.sin(
                8.0 * t11
            ) * a8 / 8.0) / a0
        while (Math.abs(t22 - t11) > Math.pow(10.0, -14.0)) {
            t11 = t22
            t22 =
                t0 + (Math.sin(2.0 * t11) * a2 / 2.0 - Math.sin(4.0 * t11) * a4 / 4.0 + Math.sin(6.0 * t11) * a6 / 6.0 - Math.sin(
                    8.0 * t11
                ) * a8 / 8.0) / a0
        }
        val Bx = t22
        val si = Math.sin(Bx)
        val N = 6378137.0 / Math.sqrt(1.0 - WGS84_Ellipsoid.e2 * si * si)
        val N2 = N * N
        val N4 = N2 * N2
        val N6 = N4 * N2
        val nuy = Math.sqrt(WGS84_Ellipsoid.e2f) * Math.cos(Bx)
        val nuy2 = nuy * nuy
        val nuy4 = nuy2 * nuy2
        val t = Math.tan(Bx)
        val t2 = t * t
        val t4 = Math.pow(t, 4.0)
        val t6 = Math.pow(t, 6.0)
        val co = Math.cos(Bx)
        val co3 = Math.pow(co, 3.0)
        val co5 = Math.pow(co, 5.0)
        val co7 = Math.pow(co, 7.0)
        val A2 = -(1.0 + nuy2) * t / (2.0 * N2)
        val B1 = 1.0 / (N * co)
        val B = Math.pow(Y, 2.0) * A2 + Bx + Math.pow(
            Y,
            4.0
        ) * (-A2 * (5.0 + 3.0 * t2 + nuy2 - 9.0 * nuy2 * t2 - 4.0 * nuy4) / (12.0 * N2)) + Math.pow(
            Y,
            6.0
        ) * ((61.0 + 90.0 * t2 + 45.0 * t4 + 46.0 * nuy2 - 252.0 * nuy2 * t2 - 90.0 * nuy2 * t4) * A2 / (360.0 * N4)) + Math.pow(
            Y,
            8.0
        ) * (-A2 * (1385.0 + 3633.0 * t2 * Bx + 4095.0 * t6 * Bx) / (20160.0 * N6))
        val L = KTtruc + (B1 * Y + Math.pow(
            Y,
            3.0
        ) * (-B1 * (1.0 + 2.0 * t2 + nuy2) / (6.0 * N2)) + Math.pow(
            Y,
            5.0
        ) * (-B1 * (5.0 + 28.0 * t2 + 6.0 * nuy2 + 8.0 * nuy2 * t2) / (120.0 * N4)) + Math.pow(
            Y,
            7.0
        ) * (-B1 * (61.0 + 662.0 * t2 + 1320.0 * t4 + 720.0 * t6) / (5040.0 * N6)))
        return doubleArrayOf(B, L)
    }
}