package de.rockyj.configuration

import de.rockyj.AppRole
import de.rockyj.handlers.RootHandler
import de.rockyj.handlers.UsersHandler
import de.rockyj.wortschatz.handlers.AuthHandler
import io.javalin.Javalin
import io.javalin.security.SecurityUtil.roles
import org.koin.standalone.KoinComponent
import org.koin.standalone.get

class Router: KoinComponent {
    fun setup(app: Javalin) {
        val rootHandler: RootHandler = get()
        val usersHandler: UsersHandler = get()
        val authHandler: AuthHandler = get()

        // Routes
        app.get("/",        { rootHandler.get(it) },    roles(AppRole.ANYONE))
        app.post("/auth",   { authHandler.create(it) }, roles(AppRole.ANYONE))
        app.get("/users",   { usersHandler.index(it) }, roles(AppRole.USER))
    }
}
