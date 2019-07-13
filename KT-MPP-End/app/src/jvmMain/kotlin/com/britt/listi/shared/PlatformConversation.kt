package com.britt.listi.shared

import data.ConversationResponse

actual typealias PlatformConversation = JVMConversation

@JvmName("getIdJVM")
actual fun PlatformConversation.getId(): String {
    return conversationId
}

actual val PlatformConversation.currentMemberId: String?
    get() = null
