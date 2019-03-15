package de.rockyj.repositories

import de.rockyj.models.User

class UserRepository(private val db: DB) {
    fun getAllUsers(): List<User> {
        val em = db.getEntityManager()
        val query = em.createQuery("SELECT u FROM users u", User::class.java)
        return query.resultList
    }
}
