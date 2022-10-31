package infrastructure

import domain.changesobserver.ChangesConfig
import infrastructure.event.EventBus
import infrastructure.exceptions.ExceptionHandler
import io.javalin.Javalin
import domain.subscription.SubscriptionConfig
import java.time.Clock

class AppConfig {
    private val eventBus = EventBus()
    private val subscriptionConfig = SubscriptionConfig(eventBus, Clock.systemDefaultZone())
    private val changesConfig = ChangesConfig(eventBus)
    private val exceptionHandler = ExceptionHandler()

    private fun registerControllers(app: Javalin) {
        subscriptionConfig.registerSubscriptionController(app)
        changesConfig.registerChangesController(app)
    }

    private fun registerExceptionHandlers(app: Javalin) {
        exceptionHandler.registerHandlers(app)
    }

    companion object {
        @JvmStatic
        fun buildApp(): Javalin {
            val appConfig = AppConfig()
            val app: Javalin = Javalin.create { config ->
                config.http.defaultContentType = "application/json"
            }
            appConfig.registerControllers(app)
            appConfig.registerExceptionHandlers(app)
            return app
        }
    }
}