package infrastructure.exceptions

import com.fasterxml.jackson.databind.ObjectMapper
import io.javalin.Javalin
import io.javalin.http.Context
import domain.subscription.dto.NoSubscriptionsException

internal class ExceptionHandler {

    fun registerHandlers(app: Javalin) {
        app.exception(NoSubscriptionsException::class.java, this::noSubscriptionsExceptionHandler)
    }

    private fun noSubscriptionsExceptionHandler(exception: NoSubscriptionsException, context: Context): ErrorResponse {
        context.res().status = 404
        context.res().contentType = "application/json"
        val response = ErrorResponse("404", exception.message ?: "", context.path())
        context.res().outputStream.write(ObjectMapper().writeValueAsBytes(response))
        return response
    }
}