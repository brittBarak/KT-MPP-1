package com.britt.listi

object ItemsRepository {
    val itemsMap = mutableMapOf<String, Int>()


    fun onItemAdded(itemName: String) {
        itemsMap[itemName] = itemsMap.size
    }

    fun getItemPosition(itemName: String): Int? {
        return itemsMap[itemName]
    }

}