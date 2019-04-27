package de.rockyj

import de.rockyj.configuration.*
import de.rockyj.filters.AuthFilter
import de.rockyj.filters.CorsFilter
import de.rockyj.handlers.AuthHandler
import de.rockyj.handlers.RootHandler
import de.rockyj.handlers.UsersHandler
import de.rockyj.repositories.DB
import de.rockyj.repositories.UserRepository
import de.rockyj.services.UserService
import org.flywaydb.core.Flyway
import org.http4k.routing.RoutingHttpHandler
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.koin.dsl.module.module
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext
import org.koin.standalone.get

val mainModule = module {
    // Configuration
    single<GenericConfiguration>("configuration") { Configuration()  }
    single<GenericConfiguration>("secrets") { Secrets() }

    single { Authentication(get(name = "secrets")) }

    // Filters
    single { AuthFilter(get()) }
    single { CorsFilter() }

    // DB
    single { DataSource(get(name="configuration"), get(name="secrets")) }
    single { DB(get()) }
    single { UserRepository(get()) }

    // Services
    single { UserService(get()) }

    // Handlers
    single { AuthHandler(get()) }
    single { UsersHandler(get()) }
    single { RootHandler() }
    single { Router() }
}

object App : KoinComponent {
    fun init(): RoutingHttpHandler {
        // Migrate the DB
        val dataSource: DataSource = get()
        Flyway.configure().dataSource(dataSource.getHikariDataSource()).load().migrate()

        // Setup routing
        val router: Router = get()
        return router.setup()
    }
}

fun main() {
    // Start DI
    StandAloneContext.startKoin(listOf(mainModule))

    // Server configuration
    val port = Configuration().get("port") as Int

    // Start server
    App.init().asServer(Jetty(port)).start()
}

