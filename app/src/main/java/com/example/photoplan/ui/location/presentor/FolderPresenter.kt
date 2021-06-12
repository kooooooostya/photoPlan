package com.example.photoplan.ui.location.presentor

import android.graphics.drawable.Drawable
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.photoplan.Location
import com.example.photoplan.ui.location.FolderView

@InjectViewState
class FolderPresenter(private val location: Location): MvpPresenter<FolderView>() {

    fun getImagesCount(): Int{
        return location.getImageCount()
    }

    fun getImage(position: Int): Drawable{
        return location.getImage(position)
    }
}
