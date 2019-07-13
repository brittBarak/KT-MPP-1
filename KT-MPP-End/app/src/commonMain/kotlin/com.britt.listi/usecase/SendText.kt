package com.britt.listi.usecase

import com.britt.listi.shared.PlatformConversation

expect class SendText{
    fun execute(conversation: PlatformConversation?, itemName: String, message: String)
}