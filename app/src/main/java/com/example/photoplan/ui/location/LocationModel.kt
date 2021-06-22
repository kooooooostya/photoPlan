package com.example.photoplan.ui.location

import android.graphics.drawable.Drawable
import android.net.Uri
import com.example.photoplan.ui.data.Image
import com.example.photoplan.ui.data.Location
import com.example.photoplan.ui.data.Place
import com.example.photoplan.ui.data.PlaceToSave
import com.example.photoplan.ui.location.presentor.LocationPresenter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class LocationModel(private val presenter: LocationPresenter) {

    companion object {
        private const val dbPath = "db_path"
        lateinit var instance: LocationModel
    }

    private var database = FirebaseDatabase.getInstance()
    private var dbRef = database.getReference(dbPath)

    private val storage = FirebaseStorage.getInstance()
    private var storageRef: StorageReference = storage.getReference("image_db")



    var mPlace: Place

    init {
        mPlace = Place("")
        loadFromDb()
        instance = this
    }

    fun updateDataInDb() {
        dbRef.setValue(PlaceToSave.toPlaceToSave(mPlace))
    }

    fun addLocation(location: Location) {
        mPlace.locationList.add(location)
        updateDataInDb()
    }

    fun changeNameOfStreet(newName: String) {
        mPlace.name = newName
        updateDataInDb()
    }

    private fun loadFromDb() {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value != null) {
                    val newPlace = snapshot.getValue(PlaceToSave::class.java)!!
                    mPlace.locationList.addAll(newPlace.toPlace().locationList)
                    mPlace.name = newPlace.name
                    updateUi()
                }
                dbRef.removeEventListener(this)
            }

            override fun onCancelled(error: DatabaseError) {
                presenter.showError(error.message)
            }
        })
    }

    private fun updateUi() {
        presenter.updateUi()
    }

    private fun insertImageToDb(uri: Uri, indexToInsertImage: Int) {
        val ref: StorageReference =
            storageRef.child("images/" + System.currentTimeMillis().toString())

        val uploadTask = ref.putFile(uri)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                presenter.showError(task.exception?.message ?: "Error")
            }
            // Continue with the task to get the download URL
            ref.downloadUrl
        }.addOnFailureListener { e ->
            presenter.showError(e.message ?: "Error")
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                mPlace.locationList[indexToInsertImage]
                    .changeLastUriImage(downloadUri!!.toString())
                updateDataInDb()
            }
        }
    }

    fun insertImage(indexToInsertImage: Int, uri: Uri, drawable: Drawable) {
        mPlace.locationList[indexToInsertImage].addImage(Image("uri", drawable))
        insertImageToDb(uri, indexToInsertImage)
    }
}