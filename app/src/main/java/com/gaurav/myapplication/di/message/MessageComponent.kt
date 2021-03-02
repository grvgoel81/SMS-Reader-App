package com.gaurav.myapplication.di.message

import android.content.Context
import com.gaurav.myapplication.di.CoreComponent
import com.gaurav.myapplication.utils.AppPreferences
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import com.gaurav.myapplication.networking.Scheduler
import com.gaurav.myapplication.ui.activity.MainActivity
import com.gaurav.myapplication.ui.adapter.MessageAdapter
import com.gaurav.myapplication.utils.MessageRepo
import com.gaurav.myapplication.utils.NotificationHelper
import com.gaurav.myapplication.utils.SMSReceiver

@MessageScope
@Component(dependencies = [CoreComponent::class], modules = [MessageModule::class])
interface MessageComponent {

    fun scheduler(): Scheduler

    fun inject(mainActivity: MainActivity)
    fun inject(smsReceiver: SMSReceiver)
}

@Module
@MessageScope
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
                             scheduler: Scheduler): MessageViewModelFactory =
            MessageViewModelFactory(messageRepo, compositeDisposable, scheduler)


    @Provides
    @MessageScope
    fun compositeDisposable(): CompositeDisposable = CompositeDisposable()
}