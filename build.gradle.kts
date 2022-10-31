import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktor_version = "2.1.2"
val kotlin_version = "1.7.20"

plugins {
    id("groovy")
    kotlin("jvm") version "1.7.20"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:5.1.2")
    implementation("io.javalin:javalin-bundle:5.1.2")
    implementation("org.slf4j:slf4j-simple:2.0.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.4")

    testImplementation("org.spockframework:spock-core:2.3-groovy-4.0")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("org.apache.groovy:groovy:4.0.6")
    testImplementation("org.junit.platform:junit-platform-engine:1.9.1")
    testImplementation("org.testcontainers:spock:1.17.5")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

application {
    mainClass.set("MainKt")
}