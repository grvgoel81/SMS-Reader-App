package com.gaurav.myapplication.utils

import android.content.Context
import jp.takuji31.koreference.KoreferenceModel
import jp.takuji31.koreference.stringPreference

class AppPreferences(context: Context) : KoreferenceModel(context = context, name = "smsSharedPreferences") {
    var notificationMessageBody: String by stringPreference("notificationMesssage")
}