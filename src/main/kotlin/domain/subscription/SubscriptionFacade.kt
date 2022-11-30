package domain.subscription

import domain.subscription.dto.SubscriptionsChanged
import domain.subscription.dto.SubscriptionsPayload
import domain.subscription.dto.SubscriptionsResponse
import infrastructure.event.EventBus
import java.time.Clock
import java.time.Instant

internal class SubscriptionFacade constructor(
    private val subscriptionRepository: SubscriptionRepository,
    private val eventBus: EventBus,
    private val clock: Clock
) {
    fun findByUserId(userId: String): SubscriptionsResponse {
        return subscriptionRepository.findByUserId(userId)
            .mapToResponse()
    }

    fun update(subscription: SubscriptionsPayload): SubscriptionsResponse {
        return subscriptionRepository.update(Subscriptions.fromPayload(subscription))
            .also { eventBus.publish(createChangedEvent(it)) }
            .mapToResponse()
    }

    private fun createChangedEvent(subscriptions: Subscriptions): SubscriptionsChanged {
        return SubscriptionsChanged(
            Instant.now(clock),
            subscriptions.subscriptions.toString(),
            subscriptions.userId,
            subscriptions.mapToResponse().subscriptions
        )
    }
}