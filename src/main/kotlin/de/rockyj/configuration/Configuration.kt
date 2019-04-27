package de.rockyj.configuration

import com.uchuhimo.konf.Config
import com.uchuhimo.konf.ConfigSpec
import java.io.InputStream

object ApplicationConfiguration : ConfigSpec("server") {
    val port by required<Int>()
    val maxPoolSize by required<Int>()
    val corsOrigin by required<String>()
}

class Configuration : GenericConfiguration {

    private val content: InputStream by lazy {
        val environment = System.getProperty("application.environment")
        this::class.java.classLoader.getResource("conf/config_$environment.yaml").openStream()
    }

    private fun readConfiguration(): Config {
        return Config { addSpec(ApplicationConfiguration) }.from.yaml.inputStream(content)
    }

    override fun <T> get(key: String): T {
        val config = readConfiguration()
        return config["server.$key"]
    }
}
