package com.example.photoplan.ui.location.presentor

import android.content.Context
import com.arellomobile.mvp.MvpPresenter
import com.example.photoplan.Location
import com.example.photoplan.ui.location.LocationModel
import com.example.photoplan.ui.location.LocationView
import java.util.ArrayList


class LocationPresenter(context: Context): MvpPresenter<LocationView>() {

    private val model = LocationModel(context)

    fun updateNameOfSection(newName: String){
        model.changeNameOfStreet(newName)
    }

    fun addFolder(name: String){
        model.addLocation(Location(name))
    }

    fun getName(): String{
        return model.mPlace.name
    }

    fun getLocations(): ArrayList<Location> {
        return model.mPlace.locationList
    }
}