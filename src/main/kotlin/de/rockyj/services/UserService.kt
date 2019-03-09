package de.rockyj.services

import de.rockyj.models.User
import de.rockyj.repositories.UserRepository

object UserService {
    fun getAllUsers(): List<User> {
        return UserRepository.getAllUsers()
    }
}