package com.example.photoplan.ui.location

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import com.example.photoplan.Location
import com.example.photoplan.Place
import com.example.photoplan.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class LocationModel(context: Context) {

    companion object{
        private const val dbPath = "db_path"
    }

    private var database = FirebaseDatabase.getInstance()
    private var dbRef = database.getReference(dbPath)

    var mPlace: Place

    init {
        //loadFromDb()

        val arrayList = ArrayList<Location>()
        arrayList.add(Location("A", arrayListOf(
            ResourcesCompat.getDrawable(context.resources,R.drawable.t1, null)!!,
            ResourcesCompat.getDrawable(context.resources,R.drawable.t2, null)!!,
            ResourcesCompat.getDrawable(context.resources,R.drawable.t3, null)!!,
            ResourcesCompat.getDrawable(context.resources,R.drawable.t4, null)!!,
            ResourcesCompat.getDrawable(context.resources,R.drawable.t5, null)!!,
            ResourcesCompat.getDrawable(context.resources,R.drawable.t6, null)!!,
            ResourcesCompat.getDrawable(context.resources,R.drawable.t7, null)!!,
        )))

        mPlace = Place("Nature", arrayList)
    }

    private fun saveAll(){
        //dbRef.setValue(mPlace)
    }

    fun addLocation(location: Location){
        mPlace.locationList.add(location)
        saveAll()
    }

    fun changeNameOfStreet(newName: String){
        mPlace.name =  newName
        saveAll()
    }



    private fun loadFromDb(){
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                mPlace = snapshot.getValue(Place::class.java)!!
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}