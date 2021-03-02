package com.gaurav.myapplication.networking

import io.reactivex.Scheduler

/**
 *  Interface to mock different threads.
* */
interface Scheduler {
    fun mainThread():Scheduler
    fun io():Scheduler
}