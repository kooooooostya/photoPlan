package com.example.photoplan.ui.location

import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.annotation.NonNull
import com.example.photoplan.ui.data.*
import com.example.photoplan.ui.location.presentor.LocationPresenter
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask


class LocationModel(private val presenter: LocationPresenter) {

    companion object {
        private const val dbPath = "db_path"
    }

    private var database = FirebaseDatabase.getInstance()
    private var dbRef = database.getReference(dbPath)

    private val storage = FirebaseStorage.getInstance()
    private var storageRef: StorageReference = storage.getReference("image_db")

    var mPlace: Place

    init {
        mPlace = Place("")
        loadFromDb()
    }

    private fun saveAll() {
        dbRef.setValue(PlaceToSave.toPlaceToSave(mPlace))
    }

    fun addLocation(location: Location) {
        mPlace.locationList.add(location)
        saveAll()
    }

    fun changeNameOfStreet(newName: String) {
        mPlace.name = newName
        saveAll()
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

        val urlTask: Task<Uri?> = uploadTask.continueWithTask { task ->
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
                saveAll()
            }
        }
    }

    fun insertImage(indexToInsertImage: Int, uri: Uri, drawable: Drawable) {
        mPlace.locationList[indexToInsertImage].addImage(Image("uri", drawable))
        insertImageToDb(uri, indexToInsertImage)
    }

}