package com.britt.listi.shared

import com.nexmo.client.NexmoConversation

actual typealias PlatformConversation = NexmoConversation

actual fun PlatformConversation.getId(): String {
    return conversationId
}

actual val PlatformConversation.currentMemberId: String?
    get() = myMember.memberId

var activeConversation: PlatformConversation? = null




