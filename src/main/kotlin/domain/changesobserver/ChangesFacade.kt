package domain.changesobserver

import domain.subscription.dto.SubscriptionsChanged
import infrastructure.event.EventBus
import io.javalin.websocket.WsContext

internal class ChangesFacade (
    eventBus: EventBus
) {
    private val sessions = mutableMapOf<String, WsContext>()

    init {
        eventBus.listen(SubscriptionsChanged::class.java, this::onEvent)
    }

    fun openUserSession(userId: String, context: WsContext) {
        sessions[userId] = context
    }

    private fun onEvent(event: SubscriptionsChanged) {
        sessions[event.userId]?.send(event.message)
    }

    fun closeUserSession(userId: String, context: WsContext) {
        sessions.remove(userId)
    }
}