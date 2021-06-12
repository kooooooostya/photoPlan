package com.example.photoplan

import android.app.Application

class PhotoPlanApplication : Application() {

    companion object {
        lateinit var instance: PhotoPlanApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}