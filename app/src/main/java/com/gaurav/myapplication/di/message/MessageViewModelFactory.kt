package com.gaurav.myapplication.di.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gaurav.myapplication.networking.Scheduler
import com.gaurav.myapplication.utils.MessageRepo
import com.gaurav.myapplication.viewmodel.MessageViewModel
import io.reactivex.disposables.CompositeDisposable

@Suppress("UNCHECKED_CAST")
class MessageViewModelFactory(private val messageRepo: MessageRepo, private val compositeDisposable: CompositeDisposable, private val scheduler: Scheduler) :
        ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MessageViewModel(messageRepo, compositeDisposable, scheduler) as T
    }
}