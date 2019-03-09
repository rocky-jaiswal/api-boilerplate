package de.rockyj.repositories

import de.rockyj.models.User
import org.jdbi.v3.sqlobject.kotlin.onDemand
import org.jdbi.v3.sqlobject.statement.SqlQuery

internal interface UserDao {
    @SqlQuery("SELECT * FROM users")
    fun listUsers(): List<User>
}

class UserRepository(private val db: DB) {

    fun getAllUsers(): List<User> {
        val dao = db.getJdbi().onDemand<UserDao>()
        return dao.listUsers()
    }
}