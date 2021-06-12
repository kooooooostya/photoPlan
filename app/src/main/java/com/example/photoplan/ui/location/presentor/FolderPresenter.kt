package com.example.photoplan.ui.location.presentor

import android.content.Context
import android.graphics.drawable.Drawable
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.photoplan.Location
import com.example.photoplan.ui.ImageDialog.ImageExpandDialog
import com.example.photoplan.ui.location.FolderView

@InjectViewState
class FolderPresenter(private val location: Location): MvpPresenter<FolderView>(){

    var imageExpandDialog = ImageExpandDialog()

    fun getImagesCount(): Int{
        return location.getImageCount()
    }

    fun getImage(position: Int): Drawable{
        return location.getImage(position)
    }

    fun expandImage(context: Context, position: Int) {
        imageExpandDialog.showDialog(context, location.getImage(position))
    }

}
