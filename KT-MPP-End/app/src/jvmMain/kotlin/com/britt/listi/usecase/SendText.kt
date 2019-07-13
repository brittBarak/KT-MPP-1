package com.britt.listi.usecase

import com.britt.listi.NexmoApi
import com.britt.listi.shared.*

actual class SendText {
    actual fun execute(
        conversation: PlatformConversation?,
        itemName: String,
        message: String
    ) {
        val event = MyCustomTextEvent(
            type = TYPE_TEXT,
            body = CustomEventBody(item_name = itemName, message = message)
        )
        NexmoApi().sendEvent(event)
    }

}