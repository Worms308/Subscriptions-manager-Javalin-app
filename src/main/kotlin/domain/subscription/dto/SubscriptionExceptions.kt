package domain.subscription.dto

import java.lang.Exception

class NoSubscriptionsException(userId: String) : Exception("User with given ID [$userId] does not exist")

class UpdatingSubscriptionsException(userId: String): Exception("Failed to update subscriptions for user [$userId]")

