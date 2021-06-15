//package com.example.photoplan.ui.location.presentor
//
//import android.graphics.drawable.Drawable
//import com.example.photoplan.Location
//import com.example.photoplan.ui.location.SectionRVView
//import moxy.InjectViewState
//import moxy.MvpPresenter
//
//
//@InjectViewState
//class SectionRVPresenter: MvpPresenter<SectionRVView>() {
//    companion object {
//        const val REQUEST_KEY = "request_key"
//    }
//    private lateinit var locationList: ArrayList<Location>
////
//    //    fun setLocationList(locations: ArrayList<Location>){
////        locationList = locations
////    }
//
//    init {
//        locationList = ArrayList()
//    }
//
//    fun getItemCount() : Int {
//
//        return locationList.size
//    }
//
//    fun getLocation(position: Int): Location {
//        return locationList[position]
//    }
//
//    fun insertImage(index: Int, drawable: Drawable) {
//        locationList[index].addImage(drawable)
//    }
//
//    fun changeLocationName(index: Int, newName: String) {
//        locationList[index].name = newName
//        //updateData()
//    }
//
//    private fun updateData() {
//        TODO("Not yet implemented")
//    }
//
//
//}