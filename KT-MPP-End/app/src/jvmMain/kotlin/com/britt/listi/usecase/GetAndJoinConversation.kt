package com.britt.listi.usecase

import com.britt.listi.NexmoApi

val nexmoApi = NexmoApi()

actual fun createAndJoinConversation(convName: String, username: String?, onFailure: () -> Unit) {
    nexmoApi.createAndJoinConversation(convName, username, onFailure)
}

actual fun getAndJoinConversation(convName: String) {
    nexmoApi.getAndJoinConversation(convName)
}