package de.rockyj.handlers

import de.rockyj.wortschatz.configuration.Authentication
import org.http4k.core.*
import org.http4k.format.Jackson
import org.http4k.format.Jackson.json
import org.slf4j.LoggerFactory

class AuthHandler(private val authentication: Authentication) {
    private val logger = LoggerFactory.getLogger(this::class.java.name)
    private val json = Jackson

    fun create(request: Request): Response {
        logger.info("Received request for auth path ...")
        return Response(Status.OK).with(Body.json().toLens() of json.obj("token" to json.string(authentication.createToken())))
    }
}
