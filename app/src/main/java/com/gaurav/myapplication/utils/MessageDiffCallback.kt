package com.gaurav.myapplication.utils

import androidx.recyclerview.widget.DiffUtil
import com.gaurav.myapplication.data.MessageData

class MessageDiffCallback(private val oldList: List<MessageData>, private val newList: List<MessageData>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].messageBody === newList[newItemPosition].messageBody
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val (_, person, time) = oldList[oldItemPosition]
        val (_, person1, time1) = newList[newItemPosition]

        return person == person1 && time == time1
    }

}