package com.example.mymap.listener

import PlanningInfo

interface LandInfoBDSListener {
    fun onLoadLandInfoSuccess(body: PlanningInfo?)
    fun onClickMap()
    fun onLoadOChucNangInfoSucces(maQHPK: String?)

}