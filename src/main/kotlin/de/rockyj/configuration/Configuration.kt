package de.rockyj.configuration

import com.uchuhimo.konf.Config
import com.uchuhimo.konf.ConfigSpec
import java.io.InputStream

object ApplicationConfiguration : ConfigSpec("server") {
    val port by required<Int>()
}

object Configuration {
    fun readConfiguration(): Config {
        val content = readConfigFile()
        return  Config{ addSpec(ApplicationConfiguration) }.from.yaml.inputStream(content)
    }

    private fun readConfigFile(): InputStream {
        val environment = System.getProperty("application.environment")
        return this::class.java.classLoader.getResource("conf/config_$environment.yaml").openStream()
    }
}
