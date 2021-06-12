package com.example.photoplan.ui.location.presentor

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.photoplan.Location
import com.example.photoplan.ui.location.SectionRVView

@InjectViewState
class SectionRVPresenter(private val locationList: ArrayList<Location>): MvpPresenter<SectionRVView>() {


    fun getItemCount() : Int {
        return locationList.size
    }

    fun getLocation(position: Int): Location {
        return locationList[position]
    }
}