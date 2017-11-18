package com.nunez.libellis

import android.app.Application
import io.realm.Realm

class LibellisAplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }

}