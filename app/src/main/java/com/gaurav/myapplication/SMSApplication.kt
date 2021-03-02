package com.gaurav.myapplication

import android.app.Application
import com.gaurav.myapplication.di.AppModule
import com.gaurav.myapplication.di.CoreComponent
import com.gaurav.myapplication.di.DaggerCoreComponent

open class SMSApplication : Application() {

    companion object {
        lateinit var coreComponent: CoreComponent
    }

    override fun onCreate() {
        super.onCreate()
        coreComponent = DaggerCoreComponent.builder().appModule(AppModule(this)).build()
    }
}