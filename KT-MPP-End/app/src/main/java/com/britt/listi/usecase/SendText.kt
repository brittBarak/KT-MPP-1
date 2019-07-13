package com.britt.listi.usecase

import com.britt.listi.shared.CustomEventBody
import com.britt.listi.shared.MyCustomTextEvent
import com.britt.listi.shared.TYPE_TEXT
import com.nexmo.client.NexmoConversation
import com.nexmo.client.request_listener.NexmoApiError
import com.nexmo.client.request_listener.NexmoRequestListener
import kotlinx.serialization.json.Json

actual class SendText {
    actual fun execute(conversation: NexmoConversation?, itemName: String, message: String) {
        val body = CustomEventBody(item_name = itemName, message = message)
        conversation?.apply {
            val event = MyCustomTextEvent(TYPE_TEXT, conversationId, myMember.memberId, body)

            val json = Json.stringify(MyCustomTextEvent.serializer(), event)
            sendText(json, object : NexmoRequestListener<Void> {
                override fun onSuccess(void: Void?) {}

                override fun onError(error: NexmoApiError?) {}

            })

        }
    }
}

