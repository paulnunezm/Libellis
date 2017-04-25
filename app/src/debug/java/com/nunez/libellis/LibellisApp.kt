package com.nunez.libellis

import android.app.Application
import com.facebook.stetho.Stetho

class LibellisApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Create an InitializerBuilder
        val initializerBuilder = Stetho.newInitializerBuilder(this)

        // Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        )

        //// Enable command line interface
        //    initializerBuilder.enableDumpapp(
        //        Stetho.defaultDumperPluginsProvider(context)
        //    );

        // Use the InitializerBuilder to generate an Initializer
        val initializer = initializerBuilder.build()

        // Initialize Stetho with the Initializer
        Stetho.initialize(initializer)
    }
}
