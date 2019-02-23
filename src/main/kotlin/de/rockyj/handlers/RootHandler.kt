package de.rockyj.handlers

import de.rockyj.App
import io.javalin.Context
import org.slf4j.LoggerFactory

object RootHandler {
    private val logger = LoggerFactory.getLogger(this::class.java.name)

    fun get(ctx: Context): Context {
        logger.info("Received request for root path ...")
        return ctx.json(mapOf("message" to App().greeting))
    }
}

