package com.core.base.utils

import android.content.Context
import jp.takuji31.koreference.KoreferenceModel
import jp.takuji31.koreference.stringPreference

class AppPreferences(context: Context) : KoreferenceModel(context = context, name = "my_preferences") {
    var notificationMessageBody: String by stringPreference("notificationMessageBody")
}