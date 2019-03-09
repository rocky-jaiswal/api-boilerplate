package de.rockyj

import de.rockyj.configuration.Configuration
import de.rockyj.handlers.RootHandler
import de.rockyj.handlers.UsersHandler
import io.javalin.Javalin

class App {
    val greeting: String
        get() {
            return "Hello world!"
        }
}

fun main() {
    val port: Int = Configuration.get("port")
    val app = Javalin.create().start(port)

    // Routes
    app.get("/") { RootHandler.get(it) }
    app.get("/users") { UsersHandler.index(it) }
}
