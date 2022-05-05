package com.example.mymap.Converter

class XL_Goc {
    fun goc2ddmmss(goc: Double): Double {
        val pdo = goc.toInt()
        val pph = (goc - pdo.toDouble()) * 60.0
        val phut = pph.toInt()
        return (pdo * 10000 + phut * 100) as Double + (pph - phut.toDouble()) * 60.0
    }
}