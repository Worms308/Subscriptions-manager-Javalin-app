package domain.subscription

internal interface SubscriptionRepository {

    fun findByUserId(userId: String): Subscriptions

    fun update(subscriptions: Subscriptions): Subscriptions
}
