package de.rockyj.repositories

import de.rockyj.configuration.DataSource
import org.jdbi.v3.core.Jdbi

class DB(private val dataSource: DataSource) {

    fun getJdbi(): Jdbi {
        val jdbi = Jdbi.create(dataSource.dataSource)
        jdbi.installPlugins()
        return jdbi
    }
}
