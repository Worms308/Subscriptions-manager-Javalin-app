import infrastructure.AppConfig

fun main(args: Array<String>) {
    AppConfig.buildApp()
        .start(8080)
}
