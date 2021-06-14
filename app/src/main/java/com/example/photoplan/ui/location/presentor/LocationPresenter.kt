package com.example.photoplan.ui.location.presentor

import android.content.Context
import com.arellomobile.mvp.MvpPresenter
import com.example.photoplan.Location
import com.example.photoplan.ui.location.LocationModel
import com.example.photoplan.ui.location.LocationView
import com.example.photoplan.ui.location.adapters.SectionRVAdapter
import java.util.ArrayList


class LocationPresenter(context: Context): MvpPresenter<LocationView>() {

    private val model = LocationModel(context)

    fun updateNameOfSection(newName: String){
        model.changeNameOfStreet(newName)
    }

    fun addFolder(adapter: SectionRVAdapter){
        model.addLocation(Location())
        adapter.notifyItemInserted(adapter.itemCount)
    }

    fun getName(): String{
        return model.mPlace.name
    }

    fun getLocations(): ArrayList<Location> {
        return model.mPlace.locationList
    }
}