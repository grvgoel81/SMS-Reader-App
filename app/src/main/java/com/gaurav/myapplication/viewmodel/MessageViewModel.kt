package com.gaurav.myapplication.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gaurav.myapplication.data.MessageData
import com.gaurav.myapplication.di.message.MessageDH
import com.gaurav.myapplication.networking.Scheduler
import com.gaurav.myapplication.utils.MessageRepo
import com.gaurav.myapplication.utils.extensions.add
import com.gaurav.myapplication.utils.extensions.performOnBackOutOnMain
import io.reactivex.disposables.CompositeDisposable

class MessageViewModel(
    private var messageRepo: MessageRepo,
    private val compositeDisposable: CompositeDisposable,
    private val scheduler: Scheduler
) : ViewModel() {

    private val errorMutableLiveData: MutableLiveData<String> = MutableLiveData()
    private val messageListLiveData: MutableLiveData<List<MessageData>> = MutableLiveData()

    fun messagesLiveData(): LiveData<List<MessageData>> = messageListLiveData
    fun errorLiveData(): LiveData<String> = errorMutableLiveData


    fun loadMessages(pageNo: Int, context: Context) {
        messageRepo.readMessagesFromInbox(context.contentResolver, pageNo)
            .performOnBackOutOnMain(scheduler)
            .subscribe({
                messageListLiveData.value = it
            }, { error -> handleError(error) })
            .add(compositeDisposable)
    }

    private fun handleError(error: Throwable?) {
        errorMutableLiveData.value = error?.localizedMessage
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        MessageDH.destroyMessageComponent()
    }

}