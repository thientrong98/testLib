package com.example.mymap.Converter

class XYVN_2_BL84 {
    fun xyvn_to_bl84(X: Double, Y: Double, KTtruc: Double, zone: Double): DoubleArray? {
        val BL: DoubleArray = XY_BL_UTM.XY2BL(X, Y, KTtruc, zone)
        BL[0] = XL_Goc().goc2ddmmss(BL[0] * 180.0 / 3.141592653589793)
        BL[1] = XL_Goc().goc2ddmmss(BL[1] * 180.0 / 3.141592653589793)
        return XYZ_2_XYZ.BL2BL.BLH_vn2000_2_wgs84(BL[0], BL[1], 0.0)
    }
}