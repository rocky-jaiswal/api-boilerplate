package de.rockyj.configuration

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.lang.Exception
import javax.sql.DataSource

object DataSource {
    private val config = HikariConfig()
    private val dbURL = Secrets.get("dbURL")
    private var dataSource: DataSource

    init {
        config.jdbcUrl = dbURL
        config.username = Secrets.get("username")
        config.password = Secrets.get("password")
        config.maximumPoolSize = Configuration.get("maxPoolSize")
        dataSource = HikariDataSource(config)
    }

    fun get(): DataSource {
        return dataSource
    }
}