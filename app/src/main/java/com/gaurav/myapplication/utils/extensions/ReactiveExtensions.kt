package com.gaurav.myapplication.utils.extensions

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import com.gaurav.myapplication.networking.Scheduler

/**
 * Extension function to subscribe on the background thread and observe on the main thread for a Completable
 * */
fun Completable.performOnBackOutOnMain(scheduler: Scheduler): Completable {
    return this.subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
}

/**
 * Extension function to subscribe on the background thread and observe on the main thread  for a Flowable
 * */
fun <T> Flowable<T>.performOnBackOutOnMain(scheduler: Scheduler): Flowable<T> {
    return this.subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
}

/**
 * Extension function to subscribe on the background thread and observe on the main thread for a Observable
 * */
fun <T> Observable<T>.performOnBackOutOnMain(scheduler: Scheduler): Observable<T> {
    return this.subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
}

/**
 * Extension function to add a Disposable to a CompositeDisposable
 */
fun Disposable.add(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

/**
 * Extension function to subscribe on the background thread for a Flowable
 * */
fun <T> Flowable<T>.performOnBack(scheduler: Scheduler): Flowable<T> {
    return this.subscribeOn(scheduler.io())
}

/**
 * Extension function to subscribe on the background thread for a Completable
 * */
fun Completable.performOnBack(scheduler: Scheduler): Completable {
    return this.subscribeOn(scheduler.io())
}

/**
 * Extension function to subscribe on the background thread for a Observable
 * */
fun <T> Observable<T>.performOnBack(scheduler: Scheduler): Observable<T> {
    return this.subscribeOn(scheduler.io())
}

/**
 * Extension function to subscribe on the background thread and observe on the main thread  for a [Single]
 * */
fun <T> Single<T>.performOnBackOutOnMain(scheduler: Scheduler): Single<T> {
    return this.subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
}