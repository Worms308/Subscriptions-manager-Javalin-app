package infrastructure.event

import spock.lang.Specification

import java.time.Instant

class EventBusTest extends Specification {

    EventBus eventBus = new EventBus()
    Instant timestamp = Instant.parse("2020-08-20T20:00:00.00Z")
    String message = "Hello message"
    String userId = "123"

    int counter = 0

    def "should register single listener"() {
        given:
            Class<Event> clazz = MockEvent.class
            Event event = new MockEvent(timestamp, message, userId)

        when:
            def registered = eventBus.listen(clazz, this::testMethod)

        then:
            registered.size() == 1
    }

    def "should register single listener and push event on it"() {
        given:
            Class<Event> clazz = MockEvent.class
            Event event = new MockEvent(timestamp, message, userId)

        when:
            def registered = eventBus.listen(clazz, this::testMethod)

        then:
            registered.size() == 1

        when:
            eventBus.publish(event)

        then:
            counter == 1
    }

    def testMethod(MockEvent arg) {
        counter++
    }
}

class MockEvent implements Event {
    Instant timestamp
    String message
    String userId

    MockEvent(Instant timestamp, String message, String userId) {
        this.timestamp = timestamp
        this.message = message
        this.userId = userId
    }
}