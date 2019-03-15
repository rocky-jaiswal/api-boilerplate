package de.rockyj.configuration

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

class DataSource(private val configuration: GenericConfiguration, private val secrets: StringConfiguration) {
    private val config = HikariConfig()
    private val dbURL = secrets.get("dbURL")
    private var ref: HikariDataSource? = null

    init {
        config.jdbcUrl = dbURL
        config.username = secrets.get("username")
        config.password = secrets.get("password")
        config.maximumPoolSize = configuration.get("maxPoolSize")
    }

    fun getHikariDataSource(): HikariDataSource {
        return if (ref == null) {
            ref = HikariDataSource(config)
            ref!!
        } else {
            ref!!
        }
    }

}
