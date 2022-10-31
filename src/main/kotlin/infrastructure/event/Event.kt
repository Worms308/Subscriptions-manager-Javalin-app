package infrastructure.event

import java.time.Instant

interface Event {
    val timestamp: Instant
    val message: String
    val userId: String
}
