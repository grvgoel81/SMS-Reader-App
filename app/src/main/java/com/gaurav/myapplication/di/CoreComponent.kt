package com.core.base.di

import android.content.Context
import com.core.base.networking.Scheduler
import com.core.base.utils.AppPreferences
import com.squareup.picasso.Picasso
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, StorageModule::class, ImageModule::class])
interface CoreComponent {

    fun context(): Context

    fun retrofit(): Retrofit

    fun picasso(): Picasso

    fun appPrefs(): AppPreferences

    fun scheduler(): Scheduler
}