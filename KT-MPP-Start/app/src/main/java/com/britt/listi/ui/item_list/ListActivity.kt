package com.britt.listi.ui.item_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexmo.client.*
import android.app.Activity
import android.view.inputmethod.InputMethodManager
import com.britt.listi.*
import com.britt.listi.R
import com.britt.listi.shared.*
import com.britt.listi.usecase.FetchListi
import com.britt.listi.utils.ListLiveData
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.layout_new_item.*


class ListActivity : AppCompatActivity() {

    val uiModel = ListUiModel(conversationName, FetchListi())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        setUi()
    }

    val messageEventListener: NexmoMessageEventListener = object : NexmoMessageEventListener {
        override fun onTypingEvent(p0: NexmoTypingEvent) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onAttachmentEvent(p0: NexmoAttachmentEvent) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onTextEvent(event: NexmoTextEvent) {
            parseTextEvent(event.id, event.text, event.member.user.name,
                handleItemAddedEvent = {
                    ItemsRepository.onItemAdded(it.body.item_name)
                    uiModel.items.addItem(
                        ItemUiModel(name = it.body.item_name)
                    )
                },
                handleNewTextEvent = { event: MyCustomTextEvent, position: Int ->
                    val itemUiModel = uiModel.items[position]
                    itemUiModel?.events?.addItem(EventUiModel(event, uiModel.conversationLiveData.value))
                    rvItems.adapter?.notifyItemChanged(position)

                })
        }


        override fun onSeenReceipt(p0: NexmoSeenEvent) {
        }

        override fun onEventDeleted(p0: NexmoDeletedEvent) {
        }

        override fun onDeliveredReceipt(p0: NexmoDeliveredEvent) {
        }


    }

    fun setUi() {
        fabAddItem.setOnClickListener {
            groupNewItem.visibility = View.VISIBLE
        }

        btnAdd.setOnClickListener {
        }

        rvItems.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ItemsAdapter(
                uiModel.conversationLiveData,
                uiModel.items
            )
        }

        uiModel.conversationLiveData.observe(this,
            Observer<NexmoConversation> { conversation -> conversation.addMessageEventListener(messageEventListener) })

        uiModel.title.observe(this, Observer { tvListName.text = it })

        uiModel.items.observe(this, Observer { value ->
            rvItems.adapter?.let {
                value.applyChange(it)
            }
        })

    }

}


open class ListUiModel(val convName: String, val getList: FetchListi) : ViewModel() {
    val conversationLiveData = getList.execute(convName)
    val title: LiveData<String> = Transformations.map(conversationLiveData) {
        it.displayName
    }
    val items = ListLiveData<ItemUiModel>()
}

class ItemUiModel(
    val name: String,
    val events: ListLiveData<EventUiModel> = ListLiveData()
)

class EventUiModel(val text: String, val itemName: String) {
    lateinit var displayFrom: String

    constructor(event: MyCustomTextEvent) : this(event.body.message!!, event.body.item_name)

    companion object {
        operator fun invoke(event: MyCustomTextEvent, conversation: NexmoConversation?): EventUiModel {
            val eventUiModel = EventUiModel(event)
            conversation?.let {
                eventUiModel.displayFrom =
                    if (event.from == conversation.myMember.memberId) {
                        "Me"
                    } else {
                        val fromMember = conversation.getMember(event.from)
                        fromMember.user.name
                    }
            }
            return eventUiModel
        }

    }
}





