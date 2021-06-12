package com.example.photoplan

import android.graphics.drawable.Drawable

class Location(var name: String = "", private val imageList: ArrayList<Drawable> = ArrayList()) {

    fun addImage(image: Drawable) {
        imageList.add(image)
    }

    fun getImage(index: Int): Drawable {
        return imageList[index]
    }

    fun getImageCount(): Int{
        return imageList.size
    }
}