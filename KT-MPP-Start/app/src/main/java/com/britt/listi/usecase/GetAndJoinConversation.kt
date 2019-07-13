package com.britt.listi.usecase

import com.britt.listi.ListRepository
import com.britt.listi.activeConversation
import com.britt.listi.currentConversationId
import com.britt.listi.currentMemberId
import com.nexmo.client.NexmoClient
import com.nexmo.client.NexmoConversation
import com.nexmo.client.NexmoMember
import com.nexmo.client.request_listener.NexmoApiError
import com.nexmo.client.request_listener.NexmoRequestListener

actual fun createAndJoinConversation(convName: String, username: String?, onFailure: () -> Unit) {
    NexmoClient.get().newConversation(convName, convName, object : NexmoRequestListener<NexmoConversation> {
        override fun onSuccess(conversation: NexmoConversation) {
            updateCurrentConversation(conversation)
        }



        override fun onError(error: NexmoApiError) {
            onFailure()
        }
    })
}

actual fun getAndJoinConversation(convName: String) {
    println("getAndJoinConversation")

    NexmoClient.get().getConversations(object : NexmoRequestListener<MutableCollection<NexmoConversation>> {
        override fun onSuccess(conversations: MutableCollection<NexmoConversation>) {
            println("onSuccess")
            val conversation = conversations.find { conv -> conv.name == convName }
            conversation?.let { updateCurrentConversation(it) }
        }

        override fun onError(error: NexmoApiError) {
           println("onError: " + error.message)
        }
    })
}

private fun updateCurrentConversation(conversation: NexmoConversation) {
    activeConversation = conversation
    ListRepository.dataLD.value = conversation
    currentConversationId = conversation.conversationId
    currentMemberId = conversation.myMember.memberId
}