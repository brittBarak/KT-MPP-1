package com.britt.listi.usecase

import com.britt.listi.NexmoApi

actual fun sendCustomText(eventJson: String) {
    NexmoApi().sendJsonEvent(eventJson)
}