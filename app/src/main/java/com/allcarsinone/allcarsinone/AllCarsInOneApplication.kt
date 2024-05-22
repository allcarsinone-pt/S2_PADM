package com.allcarsinone.allcarsinone

import android.app.Application

class AllCarsInOneApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Globals.initAPIs()
    }
}