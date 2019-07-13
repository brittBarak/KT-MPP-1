package com.britt.listi.usecase

import androidx.lifecycle.MutableLiveData
import com.britt.listi.ListiClient
import com.britt.listi.currentJwt
import com.nexmo.client.NexmoClient
import com.nexmo.client.NexmoUser
import com.nexmo.client.request_listener.NexmoApiError
import com.nexmo.client.request_listener.NexmoRequestListener

class LoginUser {

    fun execute(username: String, password: String, onSuccess: () -> Unit, message: MutableLiveData<String>) {
//        message.value = "Getting JWT user token..."
//        ListiClient.getJWT(username,
//            success = {
//                message.postValue("Connecting to the SDK...")
//                NexmoClient.get().login(it, object : NexmoRequestListener<NexmoUser> {
//                    override fun onSuccess(p0: NexmoUser?) {
//                        message.postValue("Connected to the SDK!")
//                        onSuccess()
//                    }
//
//                    override fun onError(p0: NexmoApiError?) {
//                        message.value = "Error connecting to the SDK ${p0?.message}"
//
//                    }
//                })
//            },
//            failure = {
//                message.postValue("Failed logging in :-/ ")
//            }
//        )

        NexmoClient.get().login(currentJwt, object : NexmoRequestListener<NexmoUser> {
            override fun onSuccess(p0: NexmoUser?) {
                message.postValue("Connected to the SDK!")
                onSuccess()
            }

            override fun onError(p0: NexmoApiError?) {
                message.value = "Error connecting to the SDK ${p0?.message}"

            }
        })
    }
}
