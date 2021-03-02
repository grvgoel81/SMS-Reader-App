package com.gaurav.myapplication.di

import android.content.Context
import com.gaurav.myapplication.di.message.MessageScope
import com.gaurav.myapplication.di.message.MessageViewModelFactory
import com.gaurav.myapplication.networking.Scheduler
import com.gaurav.myapplication.ui.adapter.MessageAdapter
import com.gaurav.myapplication.utils.AppPreferences
import com.gaurav.myapplication.utils.MessageRepo
import com.gaurav.myapplication.utils.NotificationHelper
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class MessageModule {

    @Provides
    @MessageScope
    fun listAdapter(appPrefs: AppPreferences): MessageAdapter = MessageAdapter(mutableListOf(), appPrefs)

    @Provides
    @MessageScope
    fun messageRepo(): MessageRepo = MessageRepo()

    @Provides
    @MessageScope
    fun notificationHelper(context: Context, appPrefs: AppPreferences): NotificationHelper = NotificationHelper(context, appPrefs)

    /*ViewModel*/
    @Provides
    @MessageScope
    fun listViewModelFactory(messageRepo: MessageRepo,
                             compositeDisposable: CompositeDisposable,
                             scheduler: Scheduler
    ): MessageViewModelFactory =
        MessageViewModelFactory(messageRepo, compositeDisposable, scheduler)


    @Provides
    @MessageScope
    fun compositeDisposable(): CompositeDisposable = CompositeDisposable()
}