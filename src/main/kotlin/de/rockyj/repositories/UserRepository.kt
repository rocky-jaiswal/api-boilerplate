package de.rockyj.repositories

import de.rockyj.models.User
import org.jdbi.v3.sqlobject.kotlin.onDemand
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate


internal interface UserDao {
    @SqlQuery("select * from users")
    fun listUsers(): List<User>

    @SqlUpdate("insert into users (name) values (?)")
    fun insert(name: String)
}

class UserRepository(private val db: DB) {
    fun getAllUsers(): List<User> {
        val dao = db.getJdbi().onDemand<UserDao>()
        return dao.listUsers()
    }

    fun insertUser(name: String) {
        val dao = db.getJdbi().onDemand<UserDao>()
        dao.insert(name)
    }
}
