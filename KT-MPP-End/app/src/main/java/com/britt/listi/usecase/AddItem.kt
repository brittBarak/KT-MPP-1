package com.britt.listi.usecase

import com.britt.listi.activeConversation
import com.britt.listi.shared.CustomEventBody
import com.britt.listi.shared.MyCustomTextEvent
import com.britt.listi.shared.PlatformConversation
import com.britt.listi.shared.TYPE_ITEM_ADDED
import com.nexmo.client.NexmoClient
import com.nexmo.client.request_listener.NexmoApiError
import com.nexmo.client.request_listener.NexmoRequestListener
import kotlinx.serialization.json.Json

actual fun sendCustomText(eventJson: String) {
    activeConversation?.sendText(eventJson, object : NexmoRequestListener<Void> {
        override fun onSuccess(void: Void) {}
        override fun onError(error: NexmoApiError) {}
    })
}
