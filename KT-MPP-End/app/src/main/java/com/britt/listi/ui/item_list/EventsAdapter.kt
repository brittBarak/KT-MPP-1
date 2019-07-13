package com.britt.listi.ui.item_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.britt.listi.utils.ListLiveData
import com.britt.listi.R
import kotlinx.android.synthetic.main.row_item_event.view.*

class EventsAdapter(val events: ListLiveData<EventUiModel>) : RecyclerView.Adapter<EventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item_event, parent, false)

        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        events[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return events.size
    }

}


class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    lateinit var data: EventUiModel

    fun bind(eventUiModel: EventUiModel) {
        data = eventUiModel
        itemView.textView.text = "${data.displayFrom} : ${data.text}"
    }


}
