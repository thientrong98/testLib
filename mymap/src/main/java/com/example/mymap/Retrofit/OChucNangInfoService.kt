package com.example.mymap.Retrofit

import ChiTieuHonHop
import OChucNang
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface OChucNangInfoService {
    @GET("/api/qhpksdd/{id}")
    fun getOChucNang(@Path("id") id: String?): Call<OChucNang>

    @GET("/api/qhpksdd/{id}/en")
    fun getOChucNangEnglish(@Path("id") id: String?): Call<OChucNang?>?

    @GET("/api/qhpksdd/hh/{id}")
    fun getChiTieuHonHop(@Path("id") id: String?): Call<List<ChiTieuHonHop>>

    @GET("/api/qhpksdd/hh/{id}/en")
    fun getChiTieuHonHopEnlish(@Path("id") id: String?): Call<List<ChiTieuHonHop>>
}