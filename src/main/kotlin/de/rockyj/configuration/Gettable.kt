package de.rockyj.configuration

interface StringConfiguration {
    fun get(key: String): String
}

interface GenericConfiguration {
    fun get(key: String): Any
}