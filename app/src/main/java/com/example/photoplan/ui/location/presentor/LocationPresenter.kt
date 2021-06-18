package com.example.photoplan.ui.location.presentor

import android.graphics.drawable.Drawable
import android.net.Uri
import com.example.photoplan.ui.data.Location
import com.example.photoplan.ui.location.LocationModel
import com.example.photoplan.ui.location.LocationView
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.*

@InjectViewState
class LocationPresenter : MvpPresenter<LocationView>() {

    private val model = LocationModel(this)

    fun updateNameOfSection(newName: String){
        model.changeNameOfStreet(newName)
    }

    fun addFolder(){
        model.addLocation(Location())
        viewState.notifyItemInserted()
    }

    fun getName(): String{
        return model.mPlace.name
    }

    fun getLocations(): ArrayList<Location> {
        return model.mPlace.locationList
    }

    fun updateUi(){
        viewState.updateUi()
    }

    fun showError(message: String) {
        viewState.makeToast(message)
    }

    fun addImage(indexToInsertImage: Int, drawable: Drawable, uri: Uri) {
        model.insertImage(indexToInsertImage, uri, drawable)
    }
}