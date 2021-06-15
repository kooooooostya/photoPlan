//package com.example.photoplan.ui.location.presentor
//
//import android.content.Context
//import android.graphics.drawable.Drawable
//import com.example.photoplan.Location
//import com.example.photoplan.ui.imageDialog.ImageExpandDialog
//import com.example.photoplan.ui.location.FolderView
//import moxy.InjectViewState
//import moxy.MvpPresenter
//
//@InjectViewState
//class FolderPresenter: MvpPresenter<FolderView>(){
//
//    private var imageExpandDialog = ImageExpandDialog()
//
//    private lateinit var location: Location
//
//    init {
//        location = Location()
//    }
//
//
//
//    fun setLocation(location: Location){
//        this.location = location
//    }
//
//    fun getImagesCount(): Int{
//        return location.getImageCount()
//    }
//
//    fun getImage(position: Int): Drawable{
//        return location.getImage(position)
//    }
//
//    fun expandImage(context: Context, position: Int) {
//        imageExpandDialog.showDialog(context, location.getImage(position))
//    }
//
//}
