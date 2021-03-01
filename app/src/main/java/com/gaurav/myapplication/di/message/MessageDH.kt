package com.vokal.messaging.di


import com.core.base.application.CoreApp
import javax.inject.Singleton

@Singleton
object MessageDH {
    private var messageComponent: MessageComponent? = null


    fun messageComponent(): MessageComponent {
        if (messageComponent == null)
            messageComponent = DaggerMessageComponent.builder().coreComponent(CoreApp.coreComponent).build()
        return messageComponent as MessageComponent
    }

    fun destroyMessageComponent() {
        messageComponent = null
    }
}