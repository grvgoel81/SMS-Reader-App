package com.gaurav.myapplication.ui.activity

import android.os.Bundle
import android.os.Handler
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.gaurav.myapplication.R
import com.gaurav.myapplication.base.BaseActivity
import com.gaurav.myapplication.di.message.MessageDH
import com.gaurav.myapplication.di.message.MessageViewModelFactory
import com.gaurav.myapplication.ui.adapter.MessageAdapter
import com.gaurav.myapplication.utils.PaginationScrollListener
import com.gaurav.myapplication.utils.RecyclerSectionItemDecoration
import com.gaurav.myapplication.utils.extensions.*
import com.gaurav.myapplication.viewmodel.MessageViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_loader.*
import kotlinx.android.synthetic.main.layout_no_content.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    private val component by lazy { MessageDH.messageComponent() }

    @Inject
    lateinit var viewModelFactoryMessage: MessageViewModelFactory

    @Inject
    lateinit var messageAdapter: MessageAdapter
    private var isLoading: Boolean = false
    private var currentPage = 0

    private val messageViewModel: MessageViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactoryMessage).get(MessageViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        component.inject(this)
        messageViewModel.messagesLiveData().observe(this, Observer {
            it?.let {
                if (currentPage < 1) messageAdapter.updateData(it) else messageAdapter.addItems(it)
            }.orElse {
                toast(getString(R.string.no_messages_found))
            }
            progressbar.hide()
            setPadding(0f)
            isLoading = false
            if (it.isNullOrEmpty())
                cl_no_content.show()
        })

        messageViewModel.errorLiveData().observe(this, Observer { errorMessage ->
            errorMessage?.let { toast(it) }.orElse { toast(getString(R.string.no_messages_found)) }
        })
        setUpRecyclerView()
        if (checkSmsReadPermission()) {
            messageViewModel.loadMessages(currentPage++, applicationContext)
        } else {
            askMessagePermission()
        }
    }

    override fun permissionGrantedReadSms() {
        messageViewModel.loadMessages(currentPage++, applicationContext)
    }

    override fun permissionDenied() {
        progressbar.hide()
        cl_no_content.show()
        tv_error_msg.text = getString(R.string.permission_not_granted)
    }

    private fun setUpRecyclerView() {
        messageAdapter.clearAllItems()
        val sectionItemDecoration = RecyclerSectionItemDecoration(resources.getDimensionPixelSize(R.dimen.any_margin),
            true,
            messageAdapter)
        rvMessages.addItemDecoration(sectionItemDecoration)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvMessages.layoutManager = layoutManager
        rvMessages.adapter = messageAdapter
        rvMessages?.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun isLastPage(): Boolean {
                return currentPage > 3
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                if (currentPage < 2) {
                    isLoading = true
                    setPadding(36f)
                    /***
                     *   Simulate data loading delay
                     */
                    Handler().postDelayed({
                        messageViewModel.loadMessages(currentPage++, context = applicationContext)
                    }, 500)
                } else {
                    toast(getString(R.string.showing_messages))
                }
            }
        })
    }

    fun setPadding(dp: Float) {
        val margins = (rvMessages.layoutParams as FrameLayout.LayoutParams).apply {
            bottomMargin = getDp(dp)
        }
        rvMessages?.apply {
            this.layoutParams = margins
        }.also {
            if (dp > 0)
                progressbar.show()
        }

    }
}