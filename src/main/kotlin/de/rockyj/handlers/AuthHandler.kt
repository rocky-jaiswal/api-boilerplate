package de.rockyj.wortschatz.handlers

import de.rockyj.wortschatz.configuration.Authentication
import io.javalin.Context
import org.slf4j.LoggerFactory

class AuthHandler(private val authentication: Authentication) {
    private val logger = LoggerFactory.getLogger(this::class.java.name)

    fun create(ctx: Context): Context {
        logger.info("Received request for auth path ...")
        return ctx.json(mapOf("token" to authentication.createToken()))
    }
}
