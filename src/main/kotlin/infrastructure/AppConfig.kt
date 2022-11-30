package infrastructure

import domain.changesobserver.ChangesConfig
import domain.subscription.SubscriptionConfig
import infrastructure.event.EventBus
import infrastructure.exceptions.ExceptionHandler
import io.javalin.Javalin
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
            app.after("/*") { ctx ->
                ctx.header("Access-Control-Allow-Origin", "http://localhost:4200")
                ctx.header("Access-Control-Request-Method", "POST, GET, OPTIONS")
            }
            app.options("/**") { ctx ->
                ctx.header("Access-Control-Allow-Origin", "http://localhost:4200")
                ctx.header("Access-Control-Request-Method", "POST, GET, OPTIONS")
                ctx.header("Access-Control-Allow-Headers", "origin, content-type, accept, x-requested-with")
                ctx.header("Access-Control-Max-Age", "3600")
            }
            return app
        }
    }
}