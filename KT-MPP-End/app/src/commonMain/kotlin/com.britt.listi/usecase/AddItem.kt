package com.britt.listi.usecase

import com.britt.listi.currentConversationId
import com.britt.listi.currentMemberId
import com.britt.listi.shared.*
import kotlinx.serialization.json.Json

class AddItem {

    fun execute(itemName: String) {
        val body = CustomEventBody(itemName)
        val event =
            MyCustomTextEvent(TYPE_ITEM_ADDED, currentConversationId, currentMemberId, body)
        val json = Json.stringify(MyCustomTextEvent.serializer(), event)

        sendCustomText(json)

    }

}

expect fun sendCustomText(eventJson: String)


