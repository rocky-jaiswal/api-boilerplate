package de.rockyj.configuration

interface StringConfiguration {
    fun get(key: String): String
}

interface GenericConfiguration {
    fun <T>get(key: String): T
}