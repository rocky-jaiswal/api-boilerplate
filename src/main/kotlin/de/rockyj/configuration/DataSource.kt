package de.rockyj.configuration

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.util.concurrent.atomic.AtomicReference

class DataSource(configuration: GenericConfiguration, secrets: GenericConfiguration) {
    private val config = HikariConfig()
    private val dbURL = secrets.get<String>("dbURL")
    private var ref: AtomicReference<HikariDataSource?> = AtomicReference()

    init {
        config.jdbcUrl = dbURL
        config.maximumPoolSize = configuration.get("maxPoolSize") as Int
    }

    @Synchronized
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
