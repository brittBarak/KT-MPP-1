package data

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


@Serializable
data class UserTokenResponse(val user_name: String?, val jwt: String)

@Serializable
data class UserTokenRequest(val user_name: String)

@Serializable
class CreateConvRequest(val name: String?)

@Serializable
class ConversationResponse(val id: String)

@Serializable
class ListConversationsResponse(val _embedded: EmbeddedConvos)

@Serializable
class EmbeddedConvos(val conversations: Array<Convos>) {

    @Serializable
    class Convos(val uuid: String, val name: String)
}

@Serializable
class JoinBody(val user_name: String?, val action: String = "join", val channel: AppChannelBody = AppChannelBody())

@Serializable
class AppChannelBody(val type: String = "app")

@Serializable
class CreateUserBody(val name: String)

