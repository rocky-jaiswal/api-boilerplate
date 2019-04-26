package de.rockyj.handlers

import de.rockyj.services.UserService
import org.http4k.core.*
import org.http4k.format.Jackson
import org.http4k.format.Jackson.json
import org.slf4j.LoggerFactory

class UsersHandler(private val userService: UserService) {
    private val logger = LoggerFactory.getLogger(this::class.java.name)
    private val json = Jackson

    fun index(request: Request): Response {
        logger.info("Received request for user index path ...")
        val users = "users" to json.array(userService.getAllUsers().map { json.obj("name" to json.string(it.name)) })
        return Response(Status.OK).with(Body.json().toLens() of json.obj(users))
    }
}
