package com.britt.listi.shared

expect class PlatformConversation

expect fun PlatformConversation.getId() :String

expect val PlatformConversation.currentMemberId: String?
