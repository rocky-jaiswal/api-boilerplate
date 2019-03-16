package de.rockyj.configuration

import com.uchuhimo.konf.Config
import com.uchuhimo.konf.ConfigSpec
import java.io.InputStream

object ApplicationConfiguration : ConfigSpec("server") {
    val port by required<Int>()
    val maxPoolSize by required<Int>()
}

class Configuration: GenericConfiguration {

    var content: InputStream

    init {
        val environment = System.getProperty("application.environment")
        content = this::class.java.classLoader.getResource("conf/config_$environment.yaml").openStream()
    }

    private fun readConfiguration(): Config {
        return Config{ addSpec(ApplicationConfiguration) }.from.yaml.inputStream(content)
    }

    override fun get(key: String): Any {
        val config = readConfiguration()
        return config["server.$key"]
    }
}
