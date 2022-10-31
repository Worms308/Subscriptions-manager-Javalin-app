package domain.changesobserver

import infrastructure.event.EventBus
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.ws
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit

internal class ChangesConfig(
    private val eventBus: EventBus
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    private fun changesFacade(eventBus: EventBus): ChangesFacade {
        return ChangesFacade(eventBus)
    }

    fun registerChangesController(app: Javalin) {
        val controller = ChangesController(changesFacade(eventBus))
        app.routes {
            ws("/changed-subscription/{userId}") {
                it.onConnect { ctx ->
                    val userId = ctx.pathParam("userId")
                    log.info("Connected as [{}]", userId)
                    controller.onConnect(userId, ctx)
                    ctx.enableAutomaticPings(5, TimeUnit.SECONDS)
                }
                it.onClose { ctx ->
                    val userId = ctx.pathParam("userId")
                    log.info("Disconnected as [{}]", userId)
                    controller.onDisconnect(userId, ctx)
                }
            }
        }
    }
}