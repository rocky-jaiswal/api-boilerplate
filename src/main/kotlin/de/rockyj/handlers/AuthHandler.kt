package de.rockyj.handlers

import de.rockyj.configuration.Authentication
import org.http4k.core.*
import org.http4k.format.Jackson
import org.http4k.format.Jackson.json
import org.slf4j.LoggerFactory

class AuthHandler(private val authentication: Authentication) {
    private val logger = LoggerFactory.getLogger(this::class.java.name)
    private val json = Jackson

    fun create(request: Request): Response {
        logger.info("Received request for auth path ...")
        val token = json.string(authentication.createToken())
        return Response(Status.OK)
            .with(Body.json().toLens() of json.obj("token" to token))
    }
}
