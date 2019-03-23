package de.rockyj.configuration

interface GenericConfiguration {
    fun <T> get(key: String): T
}
