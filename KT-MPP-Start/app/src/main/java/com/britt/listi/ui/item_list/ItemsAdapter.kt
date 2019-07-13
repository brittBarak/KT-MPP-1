package com.britt.listi.ui.item_list

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.britt.listi.utils.ListLiveData
import com.britt.listi.R
import com.britt.listi.usecase.MakeCall
import com.nexmo.client.NexmoConversation
import kotlinx.android.synthetic.main.row_item.view.*

class ItemsAdapter(
    val conversationLd: LiveData<NexmoConversation>,
    val items: ListLiveData<ItemUiModel>
) :
    RecyclerView.Adapter<ItemsAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        val holder = ItemHolder(conversationLd, view, items)
        view.tvItemName.setOnClickListener(holder)
        view.fabSend.setOnClickListener(holder)
        return holder
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ItemHolder(
        val conversationLd: LiveData<NexmoConversation>,
        itemView: View,
        val items: ListLiveData<ItemUiModel>
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {


        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.fabSend -> {

                    itemView.etMessage.text.clear()
                }
            }
        }

        var itemPosition: Int = -1

        fun bind(position: Int) {
            itemPosition = position
            val data = items[position]!!
            itemView.tvItemName.text = data.name
            itemView.rvEvents.adapter = EventsAdapter(data.events)
        }

    }
}
