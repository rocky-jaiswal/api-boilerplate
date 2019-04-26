package de.rockyj.configuration

import de.rockyj.handlers.AuthHandler
import de.rockyj.handlers.RootHandler
import de.rockyj.handlers.UsersHandler
import org.http4k.core.Method
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.koin.standalone.KoinComponent
import org.koin.standalone.get

class Router: KoinComponent {
    fun setup(): RoutingHttpHandler {
        val rootHandler: RootHandler = get()
        val usersHandler: UsersHandler = get()
        val authHandler: AuthHandler = get()

        // Routes
        return routes(
            "/" bind Method.GET to rootHandler::get,
            "/users" bind Method.GET to usersHandler::index,
            "/auth" bind Method.POST to authHandler::create
        )
    }
}
