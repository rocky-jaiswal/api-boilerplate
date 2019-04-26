import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin on the JVM.
    id("org.jetbrains.kotlin.jvm").version("1.3.30")

    id("com.github.johnrengelman.shadow").version("4.0.4")

    // Apply the application plugin to add support for building a CLI application.
    application
}

repositories {
    // Use jcenter for resolving your dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
    mavenCentral()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
}

dependencies {
    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Use junit 5
    testImplementation("org.junit.jupiter:junit-jupiter:5.4.0")

    // App dependencies
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.3.30")
    implementation("io.javalin:javalin:2.6.0")
    implementation("org.slf4j:slf4j-simple:1.7.25")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.9.8")

    // For config
    implementation("com.uchuhimo:konf:0.13.1")
    implementation("commons-codec:commons-codec:1.12")
    implementation("org.yaml:snakeyaml:1.21")

    // DI
    implementation("org.koin:koin-core:1.0.2")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("net.logstash.logback:logstash-logback-encoder:5.2")

    // DB Stuff
    implementation("org.xerial:sqlite-jdbc:3.27.2")
    implementation("com.zaxxer:HikariCP:3.3.0")
    implementation("org.jdbi:jdbi3-core:3.6.0")
    implementation("org.jdbi:jdbi3-sqlobject:3.6.0")
    implementation("org.jdbi:jdbi3-jodatime2:3.6.0")
    implementation("org.jdbi:jdbi3-kotlin:3.6.0")
    implementation("org.jdbi:jdbi3-kotlin-sqlobject:3.6.0")
    implementation("org.jdbi:jdbi3-sqlite:3.6.0")

    // DB Migration
    implementation("org.flywaydb:flyway-core:5.2.4")

    // DB Testing
    testImplementation("org.testcontainers:postgresql:1.10.6")

    // JWT
    implementation("com.auth0:java-jwt:3.8.0")
}

application {
    // Define the main class for the application.
    mainClassName = "de.rockyj.AppKt"
    applicationDefaultJvmArgs = listOf(
            "-Dapplication.environment=${System.getProperty("application.environment")}",
            "-Dapplication.key=${System.getProperty("application.key")}",
            "-Dapplication.iv=${System.getProperty("application.iv")}")
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
