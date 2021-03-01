package com.core.base.di

import android.content.Context
import com.core.base.networking.AppScheduler
import com.core.base.networking.Scheduler
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val context: Context) {
    @Provides
    @Singleton
    fun providesContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun scheduler(): Scheduler {
        return AppScheduler()
    }
}