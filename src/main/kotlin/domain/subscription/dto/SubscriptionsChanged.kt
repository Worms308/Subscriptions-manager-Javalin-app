package domain.subscription.dto

import infrastructure.event.Event
import java.time.Instant

data class SubscriptionsChanged(
    override val timestamp: Instant,
    override val message: String,
    override val userId: String
) : Event
