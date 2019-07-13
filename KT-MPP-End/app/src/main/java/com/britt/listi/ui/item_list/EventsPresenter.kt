package com.britt.listi.ui.item_list

//actual class EventsPresenter {
//    actual fun handleItemAddedEvent(event: MyCustomTextEvent) {
//        ItemsRepository.onItemAdded(event.body.item_name)
//        uiModel.items.addItem(
//            ListUiModel.ItemUiModel(name = event.body.item_name)
//        )
//    }
//
//    actual fun handleNewTextEvent(event: MyCustomTextEvent, position: Int) {
//        val itemUiModel = uiModel.items[position]
//        itemUiModel?.events?.addItem(EventUiModel(event))
//        rvItems.adapter?.notifyItemChanged(position)
//
//    }
//
//}