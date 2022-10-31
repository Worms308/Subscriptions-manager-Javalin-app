package domain.subscription.dto

import java.math.BigDecimal

data class SubscriptionsPayload(
    val userId: String,
    val subscriptions: List<SubscriptionPayload>
) {
    data class SubscriptionPayload(
        val serviceName: String,
        val monthlyPrice: BigDecimal
    )
}
