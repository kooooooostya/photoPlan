package com.example.photoplan.ui.data

class Location(var name: String = "", private val imageList: ArrayList<Image> = ArrayList()) {

    fun addImage(image: Image) {
        imageList.add(image)
    }

    fun getImage(index: Int): Image {
        return imageList[index]
    }

    fun getImageCount(): Int{
        return imageList.size
    }

    fun changeLastUriImage(uri: String) {
        imageList.last().uri = uri
    }
}