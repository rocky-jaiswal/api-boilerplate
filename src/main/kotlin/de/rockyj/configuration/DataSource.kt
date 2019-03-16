package de.rockyj.configuration

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.util.concurrent.atomic.AtomicReference

class DataSource(private val configuration: GenericConfiguration, private val secrets: StringConfiguration) {
    private val config = HikariConfig()
    private val dbURL = secrets.get("dbURL")
    private var ref: AtomicReference<HikariDataSource?> = AtomicReference()

    init {
        config.jdbcUrl = dbURL
        config.username = secrets.get("username")
        config.password = secrets.get("password")
        config.maximumPoolSize = configuration.get("maxPoolSize") as Int
    }

    fun getHikariDataSource(): HikariDataSource {
        return if (ref.get() == null) {
            ref.updateAndGet {
                HikariDataSource(config)
            }!!
        } else {
            ref.get()!!
        }
    }
}
