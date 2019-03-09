package de.rockyj.configuration

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource

class DataSource(private val configuration: GenericConfiguration, private val secrets: StringConfiguration) {
    private val config = HikariConfig()
    private val dbURL = secrets.get("dbURL")
    private var dataSource: DataSource

    init {
        config.jdbcUrl = dbURL
        config.username = secrets.get("username")
        config.password = secrets.get("password")
        config.maximumPoolSize = configuration.get("maxPoolSize")
        dataSource = HikariDataSource(config)
    }

    fun get(): DataSource {
        return dataSource
    }
}