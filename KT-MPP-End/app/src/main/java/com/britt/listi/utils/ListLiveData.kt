package com.britt.listi.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.recyclerview.widget.RecyclerView

class ListLiveData<T> : MediatorLiveData<ListHolder<T>> {

    constructor(mutableList: MutableList<T> = mutableListOf()) : super() {
        value = ListHolder(mutableList)
    }

    val size: Int
        get() {
            return value?.size() ?: -1
        }

    fun addItem(item: T, position: Int = value?.size() ?: 0) {
        value?.addItem(position, item)
        value = value

    }

    operator fun plus(item: T) {
        addItem(item)
    }

    fun removeItemAt(position: Int) {
        value?.removeItemAt(position)
        value = value
    }


    fun setItem(position: Int, item: T) {
        value?.setItem(position, item)
        value = value
    }

    fun itemUpdatedAt(position: Int) {
        value?.itemUpdatedAt(position)
        value = value
    }

    operator fun get(position: Int): T? {
        return value?.list?.get(position)
    }


}

data class ListHolder<T>(val list: MutableList<T>) {
    var indexChanged: Int = -1
    private var updateType: UpdateType? = null

    fun addItem(position: Int, item: T) {
        list.add(position, item)
        indexChanged = position
        updateType = UpdateType.INSERT
    }

    fun removeItemAt(position: Int) {
        val item = list[position]
        list.remove(item)
        indexChanged = position
        updateType = UpdateType.REMOVE
    }

    fun setItem(position: Int, item: T) {
        list[position] = item
        itemUpdatedAt(position)
    }

    fun itemUpdatedAt(position: Int) {
        indexChanged = position
        updateType = UpdateType.CHANGE
    }

    fun size(): Int {
        return list.size
    }

    fun applyChange(adapter: RecyclerView.Adapter<in RecyclerView.ViewHolder>) {
        updateType?.notifyChange(adapter, indexChanged)
    }

    fun clear() {
        list.clear()
        indexChanged = -1
        updateType = UpdateType.INIT
    }


    private enum class UpdateType {
        INSERT {
            override fun notifyChange(
                adapter: RecyclerView.Adapter<in RecyclerView.ViewHolder>,
                indexChanged: Int
            ) = adapter.notifyItemInserted(indexChanged)
        },
        REMOVE {
            override fun notifyChange(
                adapter: RecyclerView.Adapter<in RecyclerView.ViewHolder>,
                indexChanged: Int
            ) = adapter.notifyItemRemoved(indexChanged)
        },
        CHANGE {
            override fun notifyChange(
                adapter: RecyclerView.Adapter<in RecyclerView.ViewHolder>,
                indexChanged: Int
            ) = adapter.notifyItemChanged(indexChanged)
        },
        INIT {
            override fun notifyChange(adapter: RecyclerView.Adapter<in RecyclerView.ViewHolder>, indexChanged: Int) {
                adapter.notifyDataSetChanged()
            }
        };

        abstract fun notifyChange(
            adapter: RecyclerView.Adapter<in RecyclerView.ViewHolder>,
            indexChanged: Int
        )
    }
}

