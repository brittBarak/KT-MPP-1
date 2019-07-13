package com.britt.listi.usecase

import androidx.lifecycle.MutableLiveData
import com.britt.listi.ListRepository
import com.nexmo.client.NexmoConversation

class FetchListi {
    fun execute(convName: String): MutableLiveData<NexmoConversation> {
        return ListRepository.fetchListi(convName)
    }

}