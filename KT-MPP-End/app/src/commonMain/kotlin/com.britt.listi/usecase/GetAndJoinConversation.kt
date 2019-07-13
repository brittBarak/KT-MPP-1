package com.britt.listi.usecase

class GetAndJoinConversation {
    fun execute(convName: String, username: String?) {
        createAndJoinConversation(convName, username) {
            getAndJoinConversation(convName)
        }
    }
}

expect fun createAndJoinConversation(convName: String, username: String?, onFailure: () -> Unit)
expect fun getAndJoinConversation(convName: String)

