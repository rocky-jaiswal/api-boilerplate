package de.rockyj

import de.rockyj.configuration.*
import de.rockyj.handlers.AuthHandler
import de.rockyj.handlers.RootHandler
import de.rockyj.handlers.UsersHandler
import de.rockyj.repositories.DB
import de.rockyj.repositories.UserRepository
import de.rockyj.services.UserService
import de.rockyj.wortschatz.configuration.Authentication
import org.flywaydb.core.Flyway
import org.http4k.routing.RoutingHttpHandler
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.koin.dsl.module.module
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext
import org.koin.standalone.get

val mainModule = module {
    single<GenericConfiguration>("configuration") { Configuration()  }
    single<GenericConfiguration>("secrets") { Secrets() }
    single { Authentication(get(name = "secrets")) }

    single { DataSource(get(name="configuration"), get(name="secrets")) }
    single { DB(get()) }
    single { UserRepository(get()) }
    single { UserService(get()) }

    // Handlers
    single { AuthHandler(get()) }
    single { UsersHandler(get()) }
    single { RootHandler() }
    single { Router() }
}

internal enum class AppRole {
    ANYONE, USER
}


object App : KoinComponent {
    fun init(): RoutingHttpHandler {
        // Migrate the DB
        val dataSource: DataSource = get()
        Flyway.configure().dataSource(dataSource.getHikariDataSource()).load().migrate()

        val router: Router = get()
        return router.setup()
    }

//    fun getUserRole(ctx: Context) : Role {
//        val authentication: Authentication = get()
//        val authenticated = authentication.verifyToken(ctx.header("Authorization") ?: "")
//        return if(authenticated){
//            AppRole.USER
//        } else {
//            AppRole.ANYONE
//        }
//    }
//
//    fun checkAccess(handler: Handler, ctx: Context, permittedRoles: Set<Role>) {
//        val userRole = getUserRole(ctx)
//        if (permittedRoles.contains(userRole)) {
//            handler.handle(ctx)
//        } else {
//            ctx.status(401).result("Unauthorized")
//        }
//    }
}

fun main() {
    // Start DI
    StandAloneContext.startKoin(listOf(mainModule))

    // Server configuration
    val port = Configuration().get("port") as Int
    val corsOrigin = Configuration().get("corsOrigin") as String

    val app = App.init()

    app.asServer(Jetty(port)).start()
}

