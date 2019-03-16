package de.rockyj.utils

import org.junit.jupiter.api.extension.*
import org.testcontainers.containers.PostgreSQLContainer

class KPostgreSQLContainer : PostgreSQLContainer<KPostgreSQLContainer>()

object TestDBContainer {
    val postgresqlContainer = KPostgreSQLContainer()
}

class DBExtension : AfterAllCallback, BeforeAllCallback {

    @Throws(Exception::class)
    override fun beforeAll(context: ExtensionContext){
        TestDBContainer.postgresqlContainer.start()
    }

    @Throws(Exception::class)
    override fun afterAll(context: ExtensionContext) {
        TestDBContainer.postgresqlContainer.stop()
    }
}