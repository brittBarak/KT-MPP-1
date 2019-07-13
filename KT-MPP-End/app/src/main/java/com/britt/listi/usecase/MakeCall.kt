package com.britt.listi.usecase

import com.nexmo.client.NexmoCall
import com.nexmo.client.NexmoCallHandler
import com.nexmo.client.NexmoClient
import com.nexmo.client.request_listener.NexmoApiError
import com.nexmo.client.request_listener.NexmoRequestListener

actual class MakeCall {
    actual fun execute(callee: String) {
        NexmoClient.get().call(listOf(callee), NexmoCallHandler.IN_APP, object : NexmoRequestListener<NexmoCall> {
            override fun onSuccess(p0: NexmoCall?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onError(p0: NexmoApiError?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }
}
