package domain.subscription.dto

import java.math.BigDecimal

data class SubscriptionsResponse(
    val userId: String,
    val subscriptions: List<SubscriptionResponse>
) {
    data class SubscriptionResponse(
        val serviceName: String,
        val monthlyPrice: BigDecimal
    )
}