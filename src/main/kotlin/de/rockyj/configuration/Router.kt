package de.rockyj.configuration

import de.rockyj.filters.AuthFilter
import de.rockyj.handlers.AuthHandler
import de.rockyj.handlers.RootHandler
import de.rockyj.handlers.UsersHandler
import org.http4k.core.Method
import org.http4k.core.then
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.koin.standalone.KoinComponent
import org.koin.standalone.get

class Router: KoinComponent {
    fun setup(): RoutingHttpHandler {
        val authFilter: AuthFilter = get()

        val rootHandler: RootHandler = get()
        val usersHandler: UsersHandler = get()
        val authHandler: AuthHandler = get()

        // Routes
        return routes(
            "/" bind Method.GET to rootHandler::get,
            "/auth" bind Method.POST to authHandler::create,
            "/users" bind Method.GET to authFilter.then(usersHandler::index)
        )
    }
}
