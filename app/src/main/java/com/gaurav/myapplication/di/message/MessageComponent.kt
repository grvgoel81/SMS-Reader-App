package com.vokal.messaging.di

import android.content.Context
import com.core.base.di.CoreComponent
import com.core.base.networking.Scheduler
import com.core.base.utils.AppPreferences
import com.vokal.messaging.MessageListActivity
import com.vokal.messaging.adapter.MessageListAdapter
import com.vokal.messaging.utils.MessageHelper
import com.vokal.messaging.utils.MessageReceiver
import com.vokal.messaging.utils.NotificationHelper
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@MessageScope
@Component(dependencies = [CoreComponent::class], modules = [MessageModule::class])
interface MessageComponent {

    fun scheduler(): Scheduler

    fun inject(listActivity: MessageListActivity)
    fun inject(messageReceiver: MessageReceiver)
}

@Module
@MessageScope
class MessageModule {

    @Provides
    @MessageScope
    fun listAdapter(appPrefs: AppPreferences): MessageListAdapter = MessageListAdapter(mutableListOf(), appPrefs)

    @Provides
    @MessageScope
    fun messageHelper(): MessageHelper = MessageHelper()

    @Provides
    @MessageScope
    fun notificationHelper(context: Context, appPrefs: AppPreferences): NotificationHelper = NotificationHelper(context, appPrefs)

    /*ViewModel*/
    @Provides
    @MessageScope
    fun listViewModelFactory(messageHelper: MessageHelper,
                             compositeDisposable: CompositeDisposable,
                             scheduler: Scheduler): MessageListViewModelFactory =
            MessageListViewModelFactory(messageHelper, compositeDisposable, scheduler)


    @Provides
    @MessageScope
    fun compositeDisposable(): CompositeDisposable = CompositeDisposable()
}