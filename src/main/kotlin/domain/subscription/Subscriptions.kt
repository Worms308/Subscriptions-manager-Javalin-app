package domain.subscription

import domain.subscription.dto.SubscriptionsPayload
import domain.subscription.dto.SubscriptionsPayload.SubscriptionPayload
import domain.subscription.dto.SubscriptionsResponse
import java.math.BigDecimal

internal data class Subscriptions(
    val userId: String,
    val subscriptions: List<Subscription>
) {
    internal data class Subscription(
        val serviceName: String,
        val monthlyPrice: BigDecimal
    ) {
        companion object {
            @JvmStatic
            fun fromPayload(payload: SubscriptionPayload): Subscription =
                Subscription(payload.serviceName, payload.monthlyPrice)
        }
    }

    fun mapToResponse(): SubscriptionsResponse = SubscriptionsResponse(
        userId,
        subscriptions.map {
            SubscriptionsResponse.SubscriptionResponse(it.serviceName, it.monthlyPrice)
        }
    )

    companion object {
        @JvmStatic
        fun fromPayload(payload: SubscriptionsPayload): Subscriptions =
            Subscriptions(payload.userId, payload.subscriptions.map { Subscription.fromPayload(it) })
    }

}

