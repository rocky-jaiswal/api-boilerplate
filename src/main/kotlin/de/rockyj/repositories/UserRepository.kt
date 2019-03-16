package de.rockyj.repositories

import de.rockyj.models.User

class UserRepository(private val db: DB) {
    fun getAllUsers(): List<User> {
        val em = db.getEntityManager()
        val tx = em.transaction
        tx.begin()
        val query = em.createQuery("SELECT u FROM users u", User::class.java)
        tx.commit()
        return query.resultList
    }
}
