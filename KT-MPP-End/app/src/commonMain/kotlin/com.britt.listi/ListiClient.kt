package com.britt.listi

import com.britt.listi.shared.*
import com.britt.listi.shared.ApplicationDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import data.UserTokenRequest
import data.UserTokenResponse
import io.ktor.client.HttpClient
import io.ktor.client.features.defaultRequest
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.content.*
import kotlinx.serialization.json.Json

var currentUsername: String? = null
lateinit var currentJwt: String
var activeConversation: PlatformConversation? = null
var conversationName: String = "Listi"
var currentConversationId: String? = null
var currentMemberId: String? = null

val prefixCreateUser = "/sample/user/"
val pathCreateUser = "$prefixCreateUser{username}"
val pathGetJwt = "/sample/api/jwt"

val BASE_BACKEND_HOST = "127.0.0.1"
val BASE_BAKEND_PORT = 8080

object ListiClient {


    private val listiClient = HttpClient() {
        defaultRequest {
            host = BASE_BACKEND_HOST
            port = BASE_BAKEND_PORT
        }
    }

    fun getJWT(username: String, success: (String) -> Unit, failure: (Throwable?) -> Unit) {
        createUser(username)
        currentUsername = username
        GlobalScope.launch(ApplicationDispatcher) {
            try {
                val message = listiClient.get<String>("$pathGetJwt/$username")

                currentJwt = Json.nonstrict.parse(UserTokenResponse.serializer(), message).jwt
                    .also(success)

            } catch (ex: Exception) {
                failure(ex)
            }
        }
    }

    private fun createUser(username: String) {
        GlobalScope.launch(ApplicationDispatcher) {
            try {
                listiClient.get<String>("$prefixCreateUser$username")

            } catch (ex: Exception) {
//                failure(ex)
            }
        }
    }

}