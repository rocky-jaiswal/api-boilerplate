package de.rockyj.repositories

import de.rockyj.models.User
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper
import org.jdbi.v3.sqlobject.kotlin.onDemand
import org.jdbi.v3.sqlobject.statement.SqlQuery

internal interface UserDao {
    @SqlQuery("SELECT * FROM users")
    fun listUsers(): List<User>
}

object UserRepository {

    fun getAllUsers(): List<User> {
        val dao = getJdbi().onDemand<UserDao>()
        return dao.listUsers()
    }
}