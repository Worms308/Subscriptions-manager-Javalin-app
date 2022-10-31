package domain.changesobserver

import io.javalin.websocket.WsContext

internal class ChangesController(
    private val changesFacade: ChangesFacade
) {

    fun onConnect(userId: String, wsContext: WsContext) {
        changesFacade.openUserSession(userId, wsContext)
    }

    fun onDisconnect(userId: String, wsContext: WsContext) {
        changesFacade.closeUserSession(userId, wsContext)
    }
}