package de.rockyj.utils

import de.rockyj.configuration.DataSource
import de.rockyj.configuration.GenericConfiguration
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(DBExtension::class)
open class DBTest {

    lateinit var dataSource: DataSource

    @BeforeEach
    fun setupDataSource() {
        val jdbcURL = TestDBContainer.postgresqlContainer.jdbcUrl
        val username = TestDBContainer.postgresqlContainer.username
        val password = TestDBContainer.postgresqlContainer.password
        val maxPoolSize = 10

        val genericConfiguration: GenericConfiguration = object : GenericConfiguration {
            override fun <T> get(key: String): T {
                return when (key) {
                    "maxPoolSize" -> maxPoolSize as T
                    else -> 10 as T
                }
            }
        }
        val secretsConfiguration: GenericConfiguration = object : GenericConfiguration {
            override fun <T> get(key: String): T {
                return when (key) {
                    "dbURL" -> jdbcURL as T
                    "username" -> username as T
                    "password" -> password as T
                    else -> "" as T
                }
            }
        }

        dataSource = DataSource(genericConfiguration, secretsConfiguration)
        Flyway.configure().dataSource(dataSource.getHikariDataSource()).load().migrate()
    }

    @AfterEach
    fun cleanupDB() {
        Flyway.configure().dataSource(dataSource.getHikariDataSource()).load().clean()
    }
}
