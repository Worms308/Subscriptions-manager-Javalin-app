package subscription

import domain.subscription.SubscriptionFacade
import domain.subscription.SubscriptionRepository

class SubscriptionFacadeSpec extends SubscriptionSpec {

    SubscriptionRepository mockedRepository = Mock()
    SubscriptionFacade subscriptionFacade = new SubscriptionFacade(mockedRepository)

    def "should get empty subscriptions list"() {
        given:
            mockUserWithNoSubscriptions(mockedRepository)

        when:
            def subs = subscriptionFacade.findByUserId(userIdWithNoSubs)

        then:
            subs.subscriptions.isEmpty()
    }

    def "should get subscriptions list with single subscription"() {
        given:
            def subscription = getDefaultSub()
            mockUserWithOneSubscription(mockedRepository, subscription)

        when:
            def subs = subscriptionFacade.findByUserId(userIdWithOneSub)

        then:
            subs.subscriptions.size() == 1
            subs.subscriptions[0].serviceName == subscription.serviceName
            subs.subscriptions[0].monthlyPrice == subscription.monthlyPrice
    }

    def "should insert new subscriptions for given user"() {
        given:
            def payload = getDefaultSubPayload()

        and:
            mockUserAddedSubs(mockedRepository, payload)

        when:
            def resultSubs = subscriptionFacade.update(payload)

        then:
            resultSubs.userId == payload.userId
            resultSubs.subscriptions.size() == payload.subscriptions.size()
            resultSubs.subscriptions[0].serviceName == payload.subscriptions[0].serviceName
            resultSubs.subscriptions[0].monthlyPrice == payload.subscriptions[0].monthlyPrice
    }

}