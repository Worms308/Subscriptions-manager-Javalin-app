package subscription

import infrastructure.AppConfig
import io.javalin.http.HttpStatus
import io.javalin.testtools.JavalinTest
import okhttp3.Response
import domain.subscription.dto.SubscriptionsPayload
import domain.subscription.dto.SubscriptionsResponse


class SubscriptionsIntegrationSpec extends SubscriptionSpec {

    def app = AppConfig.buildApp()

    def "should get 404 when asking for non existing user subscriptions"() {
        given:
            def nonExistingUser = "1"
            Response response = null

        when:
            JavalinTest.test(app) { server, client ->
                response = client.get("/subscriptions/$nonExistingUser")
            }

        then:
            response.code() == 404
            response.body().string() == """{"code":"404","errorMessage":"User with given ID [$nonExistingUser] does not exist","path":"/subscriptions/$nonExistingUser"}"""
    }

    def "should create subscriptions list for new user"() {
        given:
            SubscriptionsPayload payload = getDefaultSubPayload()
            Response response = null

        when:
            JavalinTest.test(app) { server, client ->
                response = client.post("/subscriptions", payload)
            }

        then:
            response.code() == HttpStatus.CREATED.code
            with (bodyAsClass(response, SubscriptionsResponse.class)) {
                it.userId == payload.userId
                it.subscriptions.size() == payload.subscriptions.size()
                it.subscriptions[0].serviceName == payload.subscriptions[0].serviceName
                it.subscriptions[0].monthlyPrice == payload.subscriptions[0].monthlyPrice
            }
    }

    def "should add new subscriptions to existing ones"() {
        given:
            SubscriptionsPayload payload = getDefaultSubPayload()
            Response response = null

        and:
            SubscriptionsPayload payloadWithNewSub = getDefaultSubPayload(userIdWithAddedSubs, newSub())

        when:
            JavalinTest.test(app) { server, client ->
                client.post("/subscriptions", payload)
                response = client.post("/subscriptions", payloadWithNewSub)
            }

        then:
            response.code() == HttpStatus.CREATED.code
            with (bodyAsClass(response, SubscriptionsResponse.class)) {
                it.userId == payloadWithNewSub.userId
                it.subscriptions.size() == 2
                it.subscriptions[0].serviceName == payload.subscriptions[0].serviceName
                it.subscriptions[0].monthlyPrice == payload.subscriptions[0].monthlyPrice
                it.subscriptions[1].serviceName == payloadWithNewSub.subscriptions[0].serviceName
                it.subscriptions[1].monthlyPrice == payloadWithNewSub.subscriptions[0].monthlyPrice
            }
    }

    def "should change subscription price"() {
        given:
            SubscriptionsPayload payload = getDefaultSubPayload(userIdWithAddedSubs,
                    getDefaultSub([monthlyPrice: 69.99g]))
            Response response = null

        and:
            SubscriptionsPayload updatedPayload = getDefaultSubPayload(userIdWithAddedSubs,
                    getDefaultSub([monthlyPrice: 79.99g]))

        when:
            JavalinTest.test(app) { server, client ->
                client.post("/subscriptions", payload)
                response = client.post("/subscriptions", updatedPayload)
            }

        then:
            response.code() == HttpStatus.CREATED.code
            with (bodyAsClass(response, SubscriptionsResponse.class)) {
                it.subscriptions.size() == 1
                it.subscriptions[0].monthlyPrice == 79.99g
            }
    }

    def newSub() {
        return getDefaultSub([
                serviceName: "Disney +",
                monthlyPrice: 29.99g
        ])
    }
}