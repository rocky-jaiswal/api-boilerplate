package de.rockyj.repositories

import de.rockyj.configuration.DataSource
import org.jdbi.v3.core.Jdbi

fun getJdbi(): Jdbi {
    val jdbi = Jdbi.create(DataSource.get())
    jdbi.installPlugins()
    return jdbi
}