package infrastructure.event


class EventBus(
    private val listeners: MutableMap<Class<out Event>, Set<ListenerMethod<Event>>> = mutableMapOf()
) {

    fun <T: Event> publish(event: T) {
        listeners[event.javaClass]?.forEach { it.accept(event) }
    }

    fun <T: Event> listen(eventType: Class<out Event>, method: ListenerMethod<out T>): Set<ListenerMethod<Event>>? {
        return listeners.merge(eventType, mutableSetOf(method as ListenerMethod<Event>)) { a, b -> a + b }
    }
}