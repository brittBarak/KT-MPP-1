package com.britt.listi

import androidx.lifecycle.MutableLiveData
import com.britt.listi.usecase.GetAndJoinConversation
import com.nexmo.client.NexmoClient
import com.nexmo.client.NexmoConversation
import com.nexmo.client.request_listener.NexmoApiError
import com.nexmo.client.request_listener.NexmoRequestListener

object ListRepository {
    var dataLD = MutableLiveData<NexmoConversation>()

    fun fetchListi(convName: String): MutableLiveData<NexmoConversation> {
        currentUsername?.let { GetAndJoinConversation().execute(convName, it) }
        return dataLD
    }
}
