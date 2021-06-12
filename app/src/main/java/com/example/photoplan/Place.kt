package com.example.photoplan

import java.io.Serializable


class Place(var name: String, val locationList: ArrayList<Location> = ArrayList()) : Serializable{
}