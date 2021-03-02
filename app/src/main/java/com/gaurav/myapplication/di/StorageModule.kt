package com.gaurav.myapplication.di

import android.content.Context
import com.gaurav.myapplication.utils.AppPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {
    @Provides
    @Singleton
    fun providesSharedPreferences(context: Context): AppPreferences {
        return AppPreferences(context = context)
    }
}