package domain.subscription

import domain.subscription.dto.SubscriptionsPayload
import infrastructure.event.EventBus
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import java.time.Clock

class SubscriptionConfig(
    private val eventBus: EventBus,
    private val clock: Clock
) {

    private fun subscriptionFacade(): SubscriptionFacade {
        val repository = SubscriptionInMemoryRepository()
        return SubscriptionFacade(repository, eventBus, clock)
    }

    fun registerSubscriptionController(app: Javalin) {
        val controller = SubscriptionController(subscriptionFacade())
        app.routes {
            path("/subscriptions") {
                get("/{userId}") { ctx ->
                    val payload = ctx.pathParam("userId")
                    val response = controller.findUserSubscriptions(payload)
                    ctx.json(response)
                }
                post("/") { ctx ->
                    val payload = ctx.bodyAsClass(SubscriptionsPayload::class.java)
                    val response = controller.updateUserSubscriptions(payload)
                    ctx.json(response)
                    ctx.status(201)
                }
            }
        }
    }
}