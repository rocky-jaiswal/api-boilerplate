package de.rockyj.configuration

import com.uchuhimo.konf.Config
import com.uchuhimo.konf.ConfigSpec
import com.uchuhimo.konf.Spec
import java.io.InputStream

object ApplicationConfiguration : ConfigSpec("server") {
    val port by required<Int>()
    val maxPoolSize by required<Int>()
}

object Configuration {
    private fun readConfigFile(): InputStream {
        val environment = System.getProperty("application.environment")
        return this::class.java.classLoader.getResource("conf/config_$environment.yaml").openStream()
    }

    private fun readConfiguration(): Config {
        val content = readConfigFile()
        return Config{ addSpec(ApplicationConfiguration) }.from.yaml.inputStream(content)
    }

    fun <T>get(key: String): T {
        val config = readConfiguration()
        return config["server.$key"]
    }
}
