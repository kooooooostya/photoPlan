package com.example.photoplan.ui.data

import java.io.Serializable

class PlaceToSave(
    var name: String = "",
    val locationList: ArrayList<LocationToSave> = ArrayList()
) :
    Serializable {

    companion object{
        fun toPlaceToSave(place: Place): PlaceToSave {
            val res = PlaceToSave(place.name)
            val locationList = ArrayList<LocationToSave>()
            place.locationList.forEach {
                val images = ArrayList<String>()
                for (i in 0 until it.getImageCount()) {
                    images.add(it.getImage(i).uri)
                }
                locationList.add(LocationToSave(it.name, images))
            }
            res.locationList.addAll(locationList)
            return res
        }
    }

    fun toPlace(): Place{
        val res = Place(name)

        val locationList = ArrayList<Location>()
        this.locationList.forEach {
            val images = ArrayList<Image>()
            for (i in it.imageList) {
                images.add(Image(i))
            }
            locationList.add(Location(it.name, images))
        }
        res.locationList.addAll(locationList)
        return res
    }
}