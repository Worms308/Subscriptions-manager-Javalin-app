package domain.subscription

import okhttp3.internal.toImmutableList
import domain.subscription.Subscriptions.Subscription
import domain.subscription.dto.NoSubscriptionsException
import domain.subscription.dto.UpdatingSubscriptionsException

internal class SubscriptionInMemoryRepository : SubscriptionRepository {
    private val storage = mutableMapOf<String, Subscriptions>()

    override fun findByUserId(userId: String): Subscriptions {
        return storage[userId] ?: throw NoSubscriptionsException(userId)
    }

    override fun update(subscriptions: Subscriptions): Subscriptions {
        val existingSubscriptions = storage[subscriptions.userId]
        if (existingSubscriptions != null) {
            val mergedSubscriptions = subscriptions.subscriptions.toMutableList()
            mergedSubscriptions.addAll(0, getUnchangedSubs(existingSubscriptions, mergedSubscriptions))
            storage[subscriptions.userId] = Subscriptions(existingSubscriptions.userId, mergedSubscriptions.toImmutableList())
        } else {
            storage[subscriptions.userId] = subscriptions
        }
        return storage[subscriptions.userId] ?: throw UpdatingSubscriptionsException(subscriptions.userId)
    }

    private fun getUnchangedSubs(
        existingSubscriptions: Subscriptions,
        newSubscriptions: List<Subscription>
    ) = existingSubscriptions.subscriptions.filter { existing ->
        existing.serviceName !in newSubscriptions.map { it.serviceName }
    }
}