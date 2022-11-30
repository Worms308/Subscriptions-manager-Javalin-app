package domain.subscription.dto

import domain.subscription.dto.SubscriptionsResponse.SubscriptionResponse
import infrastructure.event.Event
import java.time.Instant

data class SubscriptionsChanged(
    override val timestamp: Instant,
    override val message: String,
    override val userId: String,
    val subscriptions: List<SubscriptionResponse>
) : Event
