package com.gaurav.myapplication.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.SmsMessage
import com.gaurav.myapplication.di.message.MessageDH
import javax.inject.Inject

class SMSReceiver : BroadcastReceiver() {

    private val component by lazy { MessageDH.messageComponent() }

    @Inject
    lateinit var notificationHelper: NotificationHelper

    override fun onReceive(context: Context, intent: Intent) {
        component.inject(this)
        val bundle = intent.extras
        if (intent.action.equals("android.provider.Telephony.SMS_RECEIVED", ignoreCase = true)) {
            if (bundle != null) {
                val sms = bundle[SMS_BUNDLE] as Array<Any>
                var smsMsg = ""
                var smsMessage: SmsMessage
                for (i in sms.indices) {
                    smsMessage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        val format = bundle.getString("format")
                        SmsMessage.createFromPdu(sms[i] as ByteArray, format)
                    } else {
                        SmsMessage.createFromPdu(sms[i] as ByteArray)
                    }
                    val msgBody = smsMessage.messageBody.toString()
                    val msgAddress = smsMessage.originatingAddress
                    smsMsg += "SMS from : $msgAddress\n"
                    smsMsg += """
                        $msgBody
                        """.trimIndent()
                    notificationHelper.showNotification(msgAddress, msgBody)
                }
            }
        }
    }

    companion object {
        const val SMS_BUNDLE = "pdus"
    }
}