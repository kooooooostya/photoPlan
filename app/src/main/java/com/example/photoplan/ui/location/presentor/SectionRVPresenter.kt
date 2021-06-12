package com.example.photoplan.ui.location.presentor

import android.graphics.drawable.Drawable
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.photoplan.Location
import com.example.photoplan.ui.location.SectionRVView


@InjectViewState
class SectionRVPresenter(private val locationList: ArrayList<Location>): MvpPresenter<SectionRVView>() {
    companion object {
        const val REQUEST_KEY = "request_key"
    }

    fun getItemCount() : Int {
        return locationList.size
    }

    fun getLocation(position: Int): Location {
        return locationList[position]
    }

    fun insertImage(index: Int, drawable: Drawable) {
        locationList[index].addImage(drawable)
    }


}