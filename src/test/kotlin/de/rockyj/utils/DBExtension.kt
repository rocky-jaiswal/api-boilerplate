package de.rockyj.utils

import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.testcontainers.containers.PostgreSQLContainer

class KPostgreSQLContainer() : PostgreSQLContainer<KPostgreSQLContainer>()

object DBContainer {
    val postgresqlContainer = KPostgreSQLContainer()
}

class DBExtension : AfterEachCallback, BeforeEachCallback {


    @Throws(Exception::class)
    override fun beforeEach(context: ExtensionContext) {
        DBContainer.postgresqlContainer.start()
    }

    @Throws(Exception::class)
    override fun afterEach(context: ExtensionContext) {
        DBContainer.postgresqlContainer.stop()
    }
}