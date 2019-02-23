package de.rockyj.configuration

import com.uchuhimo.konf.Config
import com.uchuhimo.konf.ConfigSpec
import java.io.File

object ApplicationConfiguration : ConfigSpec("server") {
    val port by required<Int>()
}

object Configuration {
    fun readConfiguration(): Config {
        val content = readConfigFile()
        return  Config{ addSpec(ApplicationConfiguration) }.from.yaml.file(content)
    }

    private fun readConfigFile(): File {
        val environment = System.getProperty("application.environment")
        return File(this::class.java.classLoader.getResource("config_$environment.yaml").file)
    }
}
