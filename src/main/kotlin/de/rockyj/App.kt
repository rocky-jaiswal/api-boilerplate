package de.rockyj

import de.rockyj.configuration.*
import de.rockyj.handlers.RootHandler
import de.rockyj.handlers.UsersHandler
import de.rockyj.repositories.DB
import de.rockyj.repositories.UserRepository
import de.rockyj.services.UserService
import io.javalin.Javalin
import org.flywaydb.core.Flyway
import org.koin.dsl.module.module
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext
import org.koin.standalone.get

val mainModule = module {
    single { Configuration() as GenericConfiguration }
    single { Secrets() as StringConfiguration }
    single { DataSource(get(), get()) }
    single { DB(get()) }
    single { UserRepository(get()) }
    single { UserService(get()) }
    single { UsersHandler(get()) }
    single { RootHandler() }
}

class App: KoinComponent {
    fun run(app: Javalin) {
        // Migrate the DB
        val dataSource: DataSource = get()
        Flyway.configure().dataSource(dataSource.get()).load().migrate()

        val userHandler: UsersHandler = get()
        val rootHandler: RootHandler = get()

        // Routes
        app.get("/") { rootHandler.get(it) }
        app.get("/users") { userHandler.index(it) }
    }
}

fun main() {
    // Start DI
    StandAloneContext.startKoin(listOf(mainModule))

    // Javalin setup
    val port: Int = Configuration().get("port")
    val app = Javalin.create().start(port)

    // Run the app
    App().run(app)
}
