package de.rockyj.utils

import de.rockyj.configuration.DataSource
import de.rockyj.configuration.GenericConfiguration
import de.rockyj.configuration.StringConfiguration
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

        val genericConfiguration: GenericConfiguration = object: GenericConfiguration {
            override fun get(key: String): Any {
                return when(key) {
                    "maxPoolSize" -> maxPoolSize
                    else -> 10
                }
            }
        }
        val secretsConfiguration: StringConfiguration = object: StringConfiguration {
            override fun get(key: String): String {
                return when(key) {
                    "dbURL" -> jdbcURL
                    "username" -> username
                    "password" -> password
                    else -> ""
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