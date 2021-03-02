package com.gaurav.myapplication.di.message

import com.gaurav.myapplication.SMSApplication
import javax.inject.Singleton

@Singleton
object MessageDH {
    private var messageComponent: MessageComponent? = null


    fun messageComponent(): MessageComponent {
        if (messageComponent == null)
            messageComponent = DaggerMessageComponent.builder().coreComponent(SMSApplication.coreComponent).build()
        return messageComponent as MessageComponent
    }

    fun destroyMessageComponent() {
        messageComponent = null
    }
}