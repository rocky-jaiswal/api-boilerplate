package de.rockyj.handlers

import io.javalin.Context
import org.slf4j.LoggerFactory
import de.rockyj.services.UserService

class UsersHandler(private val userService: UserService) {
    private val logger = LoggerFactory.getLogger(this::class.java.name)

    fun index(ctx: Context): Context {
        logger.info("Received request for user index path ...")
        return ctx.json(mapOf("users" to userService.getAllUsers()))
    }
}
