package de.rockyj.filters

import de.rockyj.configuration.Configuration
import org.http4k.core.Filter
import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.filter.CorsPolicy
import org.http4k.lens.Header

class CorsFilter {
    fun init() = Filter { next -> {

        // TODO: Somehow DI for congfiguration does not work
        // println(configuration.get("corsOrigin") as String)

        val policy = CorsPolicy(
            listOf(Configuration().get("corsOrigin")),
            listOf("content-type", "Authorization"),
            Method.values().toList()
        )

        val response = if (it.method == Method.OPTIONS) Response(OK) else next(it)

        val origin = it.header("Origin")
        val allowedOrigin = when {
            policy.origins.contains(origin) -> origin!!
            else -> "null"
        }

        response.with(
            Header.required("access-control-allow-origin") of allowedOrigin,
            Header.required("access-control-allow-headers") of policy.headers.joinToString(", "),
            Header.required("access-control-allow-methods") of policy.methods.map { it.name }.joinToString(", ")
        )
        }
    }
}
