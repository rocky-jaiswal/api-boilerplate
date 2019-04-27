package de.rockyj.filters

import de.rockyj.configuration.Authentication
import org.http4k.core.*

class AuthFilter(private val authentication: Authentication) : Filter {
    override fun invoke(next: HttpHandler): HttpHandler = { request ->
        request.header("Authorization")?.let { token ->
            authentication.verifyToken(token).let { _ -> next(request) }
        } ?: Response(Status.UNAUTHORIZED)
    }
}
