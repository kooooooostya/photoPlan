package com.example.photoplan.ui.location

import com.example.photoplan.Location
import com.example.photoplan.Place
import com.example.photoplan.ui.location.presentor.LocationPresenter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class LocationModel(private val presenter: LocationPresenter) {

    companion object{
        private const val dbPath = "db_path"
    }

    private var database = FirebaseDatabase.getInstance()
    private var dbRef = database.getReference(dbPath)

    var mPlace: Place

    init {
        mPlace = Place("")
        loadFromDb()
    }

    private fun saveAll(){
        dbRef.setValue(mPlace)
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
                mPlace.locationList.addAll((snapshot.getValue(Place::class.java)!!).locationList)
                updateUi()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun updateUi() {
        presenter.updateUi()
    }
}