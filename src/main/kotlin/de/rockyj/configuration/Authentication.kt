package de.rockyj.wortschatz.configuration

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import de.rockyj.configuration.GenericConfiguration

const val DUMMY_USER = "user101"

class Authentication(secrets: GenericConfiguration) {
    private val algorithm = Algorithm.HMAC256(secrets.get<String>("token"))

    fun createToken(): String {
        return JWT.create().withSubject(DUMMY_USER).sign(algorithm)
    }

    fun verifyToken(token: String): Boolean {
        return try {
            val verifier = JWT.require(algorithm).build()
            val jwt = verifier.verify(token.split(" ").last())
            jwt.subject == DUMMY_USER
        } catch (e: Throwable) {
            false
        }
    }
}
