package domain.subscription

import org.slf4j.LoggerFactory
import domain.subscription.dto.SubscriptionsPayload
import domain.subscription.dto.SubscriptionsResponse

internal class SubscriptionController(
    private val subscriptionFacade: SubscriptionFacade
) {
    private val log = LoggerFactory.getLogger(this.javaClass)

    fun findUserSubscriptions(userId: String): SubscriptionsResponse {
        log.info("Subscriptions for user [{}] fetched", userId)
        return subscriptionFacade.findByUserId(userId)
    }

    fun updateUserSubscriptions(payload: SubscriptionsPayload): SubscriptionsResponse {
        log.info("Subscriptions for user [{}] changed", payload.userId)
        return subscriptionFacade.update(payload)
    }
}