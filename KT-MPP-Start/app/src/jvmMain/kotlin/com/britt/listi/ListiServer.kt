package com.britt.listi

import com.britt.listi.usecase.GetAndJoinConversation
import com.britt.listi.usecase.nexmoApi
import data.UserTokenResponse
import io.ktor.application.*
import io.ktor.http.ContentType
import io.ktor.http.content.*
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.util.pipeline.PipelineContext
import kotlinx.serialization.json.Json

fun main() {

    embeddedServer(Netty, port = BASE_BAKEND_PORT, host = BASE_BACKEND_HOST) {
        routing {

            get("/") {
                call.respondText("Hi! Thank you for coming to this workshop!", ContentType.Text.Plain)
            }

            get(pathGetJwt) {
                createJwt()
            }
            get("$pathGetJwt/{username}") {
                val paramUsername = call.parameters["username"]
                createJwt(paramUsername)
            }

            get(pathCreateUser) {
                val paramUsername = call.parameters["username"]
                paramUsername?.let { it1 -> createUser(it1) }
            }

            get("/sample/create/{conv_name}") {
                createAndJoinConversation()
            }

            get("/sample/join/{conv_name}/{username}") {
                val paramUsername = call.parameters["username"]
                val paramConvname = call.parameters["conv_name"]
                createAndJoinConversation(paramConvname, paramUsername)
            }

        }
    }.start(wait = true)
}


private suspend fun PipelineContext<Unit, ApplicationCall>.createAndJoinConversation(convName: String? = currentUsername, username: String? = null) {
    convName?.let { it1 ->
        GetAndJoinConversation().execute(it1, username)
    }
    username?.let { nexmoApi.joinConversation(currentConversationId, it) }
    call.respondText("Joined $username to conversation: $convName", ContentType.Text.Plain)
}

private suspend fun PipelineContext<Unit, ApplicationCall>.createJwt(username: String? = null) {
    val jwtResponse = generateJwt(sub = username)
    currentJwt = jwtResponse.jwt
    val json = Json.stringify(UserTokenResponse.serializer(), jwtResponse)
    call.respond(TextContent(json, ContentType.Application.Json))
}


private suspend fun PipelineContext<Unit, ApplicationCall>.createUser(username: String) {
    nexmoApi.createUser(username)
    GetAndJoinConversation().execute(conversationName, username)
    call.respondText("created user: $username", ContentType.Text.Plain)
}
