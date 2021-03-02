package com.gaurav.myapplication.utils

import android.content.ContentResolver
import android.net.Uri
import com.gaurav.myapplication.data.MessageData
import com.github.marlonlom.utilities.timeago.TimeAgo
import io.reactivex.Single

class MessageRepo {

    fun readMessagesFromInbox(contentResolver: ContentResolver, pageNumber: Int): Single<List<MessageData>> {
        val messageList: MutableList<MessageData> = mutableListOf()
        val smsInboxCursor = contentResolver
            .query(MESSAGE_URI, null, null, null, query + pageNumber * PAGE_SIZE)
        smsInboxCursor?.let {
            val messageBody = smsInboxCursor.getColumnIndex("body")
            val address = smsInboxCursor.getColumnIndex("address")
            val date = smsInboxCursor.getColumnIndex("date")
            val person = smsInboxCursor.getColumnIndex("person")
            if (messageBody < 0 || !smsInboxCursor.moveToFirst()) return Single.just(mutableListOf()) // return empty list
            do {
                var str = "SMS from : " + smsInboxCursor.getString(address) + "\n"
                str += smsInboxCursor.getString(messageBody)
                messageList.add(MessageData(messageBody = smsInboxCursor.getString(messageBody),
                    address = smsInboxCursor.getString(address),
                    time = TimeAgo.using(smsInboxCursor.getString(date).toLong()).capitalize(),
                    person = smsInboxCursor.getString(person)
                ))
            } while (smsInboxCursor.moveToNext())
            smsInboxCursor.close()
        }
        return Single.just(messageList)
    }

    companion object {
        private const val PAGE_SIZE = 10
        val MESSAGE_URI: Uri = Uri.parse("content://sms/inbox")
        const val query: String = " date DESC limit $PAGE_SIZE offset "
    }
}