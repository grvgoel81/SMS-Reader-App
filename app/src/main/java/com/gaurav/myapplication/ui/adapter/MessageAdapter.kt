package com.gaurav.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gaurav.myapplication.R
import com.gaurav.myapplication.data.MessageData
import com.gaurav.myapplication.utils.AppPreferences
import com.gaurav.myapplication.utils.MessageDiffCallback
import com.gaurav.myapplication.utils.RecyclerSectionItemDecoration
import com.gaurav.myapplication.utils.extensions.hide
import com.gaurav.myapplication.utils.extensions.show
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.message_row.*


class MessageAdapter(
    private val messageList: MutableList<MessageData>,
    private val appPrefs: AppPreferences
) : RecyclerView.Adapter<MessageAdapter.ViewHolder>(), RecyclerSectionItemDecoration.SectionCallback {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(messageList[position], appPrefs)
    }

    override fun getItemCount(): Int = messageList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.message_row, parent, false).let {
                ViewHolder(it)
            }
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindData(messageData: MessageData, appPrefs: AppPreferences) {
            with(messageData) {
                tv_sender_name.text = address
                tv_message.text = messageBody
                tv_first_char.text = address?.take(1)
                tv_date.text = time
                messageBody?.let {
                    if (appPrefs.notificationMessageBody.contentEquals(it)) {
                        iv_status.show()
                    } else {
                        iv_status.hide()
                    }
                    appPrefs.notificationMessageBody = ""
                }
                if (time.contentEquals(itemView.context.getString(R.string.just_now))) {
                    iv_status.show()
                }
            }
        }
    }

    override fun isSection(position: Int): Boolean {
        return (position == 0 || messageList[position].time != messageList[position - 1].time)
    }

    override fun getSectionHeader(position: Int): CharSequence {
        return messageList[position].time
    }

    fun addItems(it: List<MessageData>) {
        messageList.addAll(it)
        notifyDataSetChanged()
    }

    fun clearAllItems() {
        if (messageList.isNotEmpty()) {
            messageList.clear()
            notifyDataSetChanged()
        }
    }

    fun updateData(listItems: List<MessageData>) {
        val diffCallback = MessageDiffCallback(this.messageList, listItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.messageList.addAll(listItems)
        diffResult.dispatchUpdatesTo(this)
    }
}