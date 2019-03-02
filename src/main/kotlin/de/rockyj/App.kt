package de.rockyj

import de.rockyj.configuration.Configuration
import de.rockyj.configuration.ApplicationConfiguration
import de.rockyj.configuration.Secrets
import de.rockyj.handlers.RootHandler
import io.javalin.Javalin

class App {
    val greeting: String
        get() {
            return "Hello world!"
        }
}

fun main() {
    println(Secrets.decrypt())
    val config = Configuration.readConfiguration()
    val app = Javalin.create().start(config[ApplicationConfiguration.port])

    // Routes
    app.get("/") { RootHandler.get(it) }
}
