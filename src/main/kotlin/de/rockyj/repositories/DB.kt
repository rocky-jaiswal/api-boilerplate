package de.rockyj.repositories

import de.rockyj.configuration.DataSource
import java.util.concurrent.atomic.AtomicReference
import javax.persistence.EntityManager
import javax.persistence.Persistence

class DB(private val dataSource: DataSource) {

    private var ref: AtomicReference<EntityManager?> = AtomicReference()

    fun getEntityManager(): EntityManager {
        val hikariDataSource = dataSource.getHikariDataSource()
        val props = mapOf<String, Any>(
                "javax.persistence.jdbc.user" to hikariDataSource.username,
                "javax.persistence.jdbc.password" to hikariDataSource.password,
                "javax.persistence.jdbc.url" to hikariDataSource.jdbcUrl
        )

        return if (ref.get() == null) {
            ref.updateAndGet {
                val emf = Persistence.createEntityManagerFactory("de.rockyj.api-boilerplate", props)
                emf.createEntityManager()
            }!!
        } else {
            ref.get()!!
        }
    }
}
