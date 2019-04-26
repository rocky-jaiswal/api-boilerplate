package de.rockyj

import de.rockyj.configuration.*
import de.rockyj.handlers.RootHandler
import de.rockyj.handlers.UsersHandler
import de.rockyj.repositories.DB
import de.rockyj.repositories.UserRepository
import de.rockyj.services.UserService
import de.rockyj.wortschatz.configuration.Authentication
import de.rockyj.wortschatz.handlers.AuthHandler
import io.javalin.Context
import io.javalin.Handler
import io.javalin.Javalin
import io.javalin.security.Role
import org.flywaydb.core.Flyway
import org.koin.dsl.module.module
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext
import org.koin.standalone.get

val mainModule = module {
    single<GenericConfiguration>("configuration") { Configuration()  }
    single<GenericConfiguration>("secrets") { Secrets() }
    single { DataSource(get(name="configuration"), get(name="secrets")) }
    single { DB(get()) }
    single { UserRepository(get()) }
    single { UserService(get()) }
    single { Authentication(get(name = "secrets")) }
    single { AuthHandler(get()) }
    single { UsersHandler(get()) }
    single { RootHandler() }
    single { Router() }
}

internal enum class AppRole : Role {
    ANYONE, USER
}


object App : KoinComponent {
    fun init(app: Javalin) {
        // Migrate the DB
        val dataSource: DataSource = get()
        Flyway.configure().dataSource(dataSource.getHikariDataSource()).load().migrate()

        val router: Router = get()
        router.setup(app)
    }

    fun getUserRole(ctx: Context) : Role {
        val authentication: Authentication = get()
        val authenticated = authentication.verifyToken(ctx.header("Authorization") ?: "")
        return if(authenticated){
            AppRole.USER
        } else {
            AppRole.ANYONE
        }
    }

    fun checkAccess(handler: Handler, ctx: Context, permittedRoles: Set<Role>) {
        val userRole = getUserRole(ctx)
        if (permittedRoles.contains(userRole)) {
            handler.handle(ctx)
        } else {
            ctx.status(401).result("Unauthorized")
        }
    }
}

fun main() {
    // Start DI
    StandAloneContext.startKoin(listOf(mainModule))

    // Javalin configuration
    val port = Configuration().get("port") as Int
    val corsOrigin = Configuration().get("corsOrigin") as String

    // Javalin setup
    val javalin = Javalin.create().apply {
        accessManager { handler, ctx, permittedRoles -> App.checkAccess(handler, ctx, permittedRoles) }
        enableCorsForOrigin(corsOrigin)
        port(port)
    }.start()

    // Initialize the app
    App.init(javalin)
}

