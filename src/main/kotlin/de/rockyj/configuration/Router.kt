package de.rockyj.configuration

import de.rockyj.handlers.RootHandler
import de.rockyj.handlers.UsersHandler
import io.javalin.Javalin
import org.koin.standalone.KoinComponent
import org.koin.standalone.get

class Router: KoinComponent {
    fun setup(app: Javalin) {
        val userHandler: UsersHandler = get()
        val rootHandler: RootHandler = get()

        // Routes
        app.get("/") { rootHandler.get(it) }
        app.get("/users") { userHandler.index(it) }
    }
}
