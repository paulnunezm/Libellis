package com.nunez.libellis

import android.app.Application
import com.facebook.stetho.Stetho
import com.nunez.libellis.di.AppComponent
import com.nunez.libellis.di.AppModule
import com.nunez.libellis.di.DaggerAppComponent

/**
 * Created by paulnunez on 5/15/17.
 */
class LibellisApp : Application() {

    val component: AppComponent
        get() = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()

    override fun onCreate() {
        super.onCreate()

        component.inject(this)
        initializeStetho()
    }

    private fun initializeStetho() {
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