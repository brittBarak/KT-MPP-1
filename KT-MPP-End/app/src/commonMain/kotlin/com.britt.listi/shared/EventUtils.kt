package com.britt.listi.shared

import com.britt.listi.ItemsRepository
import com.britt.listi.currentConversationId
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


val ITEM_NAME = "item_name"
val CUSTOM = "custom:"
val TYPE_ITEM_ADDED = "custom:item_added"
val TYPE_TEXT = "custom:text"
val TYPE_ITEM_IMAGE = "custom:image"
val TYPE_ITEM_CALL = "custom:call"

@Serializable
class MyCustomTextEvent(
    val type: String,
    val conversation_id: String? = currentConversationId,
    val from: String? = null,
    val body: CustomEventBody,
    var id: String? = null
)

//@Serializable
//sealed class MyCustomTextEvent(val type: String, val conversation_id: String, val from: String, val body: String) {
//    @Serializable
//    class MyItemAddedEvent(val currentConversationId: String, val fromMember: String, val itemName: String) :
//        MyCustomTextEvent(
//            type = TYPE_ITEM_ADDED, conversation_id = currentConversationId, from = fromMember,
////            conversation_id = conversationLd.currentConversationId,
////            from = conversationLd.myMember.memberId,
//            body = Json.stringify(ItemAddedBody.serializer(), ItemAddedBody(itemName))
//        )
//    @Serializable
//    class MyTextEvent(val currentConversationId: String, val fromMember: String,val message: String) :  MyCustomTextEvent(
//        type = TYPE_TEXT, conversation_id = currentConversationId, from = fromMember,
////        conversation_id = conversationLd.currentConversationId,
////        from = conversationLd.myMember.memberId,
//        body = Json.stringify(TextBody.serializer(), TextBody(message))
//    )
////    class MyImageEvent() : MyCustomTextEvent(TYPE_ITEM_IMAGE)
////    class MyCallEvent() : MyCustomTextEvent(TYPE_ITEM_CALL)
//}

//@Serializable
//data class ItemAddedBody(val item_name: String)
//
//@Serializable
//data class TextBody(val message: String)


@Serializable
data class CustomEventBody(val item_name: String, val message: String? = null)


fun parseTextEvent(eventId: String, content: String, senderUsername: String, handleItemAddedEvent: (MyCustomTextEvent) -> Unit, handleNewTextEvent: (MyCustomTextEvent, Int) -> Unit) {
    val customEvent = Json.nonstrict.parse(MyCustomTextEvent.serializer(), content)
    customEvent.id = eventId

    val itemName = customEvent.body.item_name
    when (customEvent.type) {
        TYPE_ITEM_ADDED -> {
            itemName?.let {
                handleItemAddedEvent(customEvent)
            }
        }
        TYPE_TEXT -> {
            val position = ItemsRepository.getItemPosition(itemName)
            position?.let {
                (customEvent.body.message)?.let {
                    handleNewTextEvent(customEvent, position)
                }
            }
        }

    }
}
