package com.britt.listi

import com.britt.listi.shared.ApplicationDispatcher
import com.britt.listi.shared.MyCustomTextEvent
import data.*
import io.ktor.client.HttpClient
import io.ktor.client.features.defaultRequest
import io.ktor.client.request.*
import kotlinx.serialization.json.*
import io.ktor.http.ContentType
import io.ktor.http.content.TextContent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NexmoApi {

    val BASE_NEXMO_URL = "https://api.nexmo.com/beta"

    val nexmoApiClient by lazy {
        HttpClient {
            defaultRequest {
                header("Authorization", "Bearer " + currentJwt)
            }
        }
    }

    fun sendEvent(event: MyCustomTextEvent) {
        GlobalScope.launch(ApplicationDispatcher) {
            try {
                val url = "$BASE_NEXMO_URL/conversations/${event.conversation_id}/events"
                val json = Json.stringify(MyCustomTextEvent.serializer(), event)

                val message = nexmoApiClient.post<String> {
                    url(url)
                    body = TextContent(json, ContentType.Application.Json)
                }

                println("CLIENT: Message from the server: $message")


            } catch (ex: Exception) {
//                failure(ex)
            }
        }
    }

    fun sendJsonEvent(eventJson: String) {
        GlobalScope.launch(ApplicationDispatcher) {
            try {
                val url = "$BASE_NEXMO_URL/conversations/$currentConversationId/events"
                nexmoApiClient.post<String> {
                    url(url)
                    body = TextContent(eventJson, ContentType.Application.Json)
                }
//                success()
            } catch (ex: Exception) {
//                failure(ex)
            }
        }
    }

    fun joinConversation(conversation_id: String? = currentConversationId, username: String) {
        GlobalScope.launch(ApplicationDispatcher) {
            try {
                val url = "$BASE_NEXMO_URL/conversations/$conversation_id/events"
                val json = Json.stringify(JoinBody.serializer(), JoinBody(username))
                println("JSON : " + json)

                val message = nexmoApiClient.put<String> {
                    url(url)
                    body = json
                }

                println("CLIENT: Message from the server: $message")


            } catch (ex: Exception) {
//                failure(ex)
            }
        }
    }


    fun createAndJoinConversation(convName: String, username: String?, onFailure: () -> Unit) {
        GlobalScope.launch(ApplicationDispatcher) {
            try {
                val url = "$BASE_NEXMO_URL/conversations"
                val json = Json.stringify(CreateConvRequest.serializer(), CreateConvRequest(convName))
                println("JSON : " + json)

                val post =
                    nexmoApiClient.post<String> {
                        url(url)
                        body = json
                    }

                println("CLIENT: Message from the server: $post")

                currentConversationId = Json.nonstrict.parse(ConversationResponse.serializer(), post).id
                username?.let { joinConversation(currentConversationId, it) }

            } catch (ex: Exception) {
                onFailure()
//                failure(ex)
            }
        }
    }

    fun getAndJoinConversation(convName: String) {
        GlobalScope.launch(ApplicationDispatcher) {
            try {
                val url = "$BASE_NEXMO_URL/conversations"
                val response =
                    nexmoApiClient.get<String>(url)

                println("CLIENT: Message from the server: $response")

//                conversationName = convName
                val convos = Json.nonstrict.parse(ListConversationsResponse.serializer(), response)
                val conv = convos._embedded.conversations.find { conv -> conv.name == convName }
                currentConversationId = conv?.uuid

                currentUsername?.let { joinConversation(currentConversationId, it) }

            } catch (ex: Exception) {
//                failure(ex)
            }
        }
    }

    fun createUser(username: String) {
        GlobalScope.launch(ApplicationDispatcher) {
            try {
                val url = "$BASE_NEXMO_URL/users"

                val json = Json.stringify(CreateUserBody.serializer(), CreateUserBody(username))

                val response = nexmoApiClient.post<String> {
                    url(url)
                    body = TextContent(json, ContentType.Application.Json)
                }

                println("CLIENT: Message from the server: $response")

            } catch (ex: Exception) {
//                failure(ex)
            }
        }
    }
}
