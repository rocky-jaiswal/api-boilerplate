package de.rockyj.handlers

import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.slf4j.LoggerFactory

class RootHandler {
    private val logger = LoggerFactory.getLogger(this::class.java.name)

    fun get(request: Request): Response {
        logger.info("Received request for root path ...")
        return Response(Status.OK).body("OK")
    }
}
