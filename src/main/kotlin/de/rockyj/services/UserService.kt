package de.rockyj.services

import de.rockyj.models.User
import de.rockyj.repositories.UserRepository

class UserService(private val userRepository: UserRepository) {
    fun getAllUsers(): List<User> {
        return userRepository.getAllUsers()
    }
}