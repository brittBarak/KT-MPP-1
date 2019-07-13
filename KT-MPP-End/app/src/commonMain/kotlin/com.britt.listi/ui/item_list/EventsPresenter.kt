package com.britt.listi.ui.item_list

import com.britt.listi.shared.MyCustomTextEvent

interface EventsPresenter {
    fun handleItemAddedEvent(event: MyCustomTextEvent)
    fun handleNewTextEvent(event: MyCustomTextEvent, position: Int)
}