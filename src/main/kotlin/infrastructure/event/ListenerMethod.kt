package infrastructure.event

fun interface ListenerMethod<T: Event> {
    fun accept(event: T)
}