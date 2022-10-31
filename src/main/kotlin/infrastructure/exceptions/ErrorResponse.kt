package infrastructure.exceptions

class ErrorResponse(
    val code: String,
    val errorMessage: String,
    val path: String
)