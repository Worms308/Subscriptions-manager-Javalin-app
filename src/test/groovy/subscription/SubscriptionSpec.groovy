package subscription

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import domain.subscription.SubscriptionRepository
import domain.subscription.Subscriptions
import okhttp3.Response
import spock.lang.Specification
import domain.subscription.dto.SubscriptionsPayload

class SubscriptionSpec extends Specification {

    def userIdWithNoSubs = "1"
    def userIdWithAddedSubs = "2"
    def userIdWithOneSub = "3"

    ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new KotlinModule.Builder().build())

    def <T> T bodyAsClass(Response response, Class<T> clazz) {
        objectMapper.readValue(response.body().bytes(), clazz)
    }

    def mockUserWithNoSubscriptions(SubscriptionRepository mockedRepository) {
        mockedRepository.findByUserId(userIdWithNoSubs) >> new Subscriptions(userIdWithNoSubs, Collections.emptyList())
        return mockedRepository
    }

    def mockUserWithOneSubscription(SubscriptionRepository mockedRepository, sub) {
        mockedRepository.findByUserId(userIdWithOneSub) >> new Subscriptions(userIdWithOneSub, [sub])
        return mockedRepository
    }

    def mockUserAddedSubs(SubscriptionRepository mockedRepository, payload = getDefaultSubPayload()) {
        def addedSubs = Subscriptions.fromPayload(payload)
        mockedRepository.update(_ as Subscriptions) >> addedSubs
        return mockedRepository
    }

    SubscriptionsPayload getDefaultSubPayload(userId = userIdWithAddedSubs, Subscriptions.Subscription subs = getDefaultSub()) {
        return new SubscriptionsPayload(userId, [new SubscriptionsPayload.SubscriptionPayload(subs.serviceName, subs.monthlyPrice)])
    }

    Subscriptions.Subscription getDefaultSub(Map<String, ?> params = Collections.emptyMap()) {
        def defaultParams = [
                serviceName: "Netflix",
                monthlyPrice: 69.99g
        ] + params
        return new Subscriptions.Subscription(
                defaultParams["serviceName"] as String,
                defaultParams["monthlyPrice"] as BigDecimal
        )
    }
}
