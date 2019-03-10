package de.rockyj

import de.rockyj.utils.DBContainer
import de.rockyj.utils.DBExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.sql.DriverManager

@ExtendWith(DBExtension::class)
class DummyTest {
    @Test
    fun `Basic DB Select`() {
        val jdbcURL = DBContainer.postgresqlContainer.jdbcUrl
        val username = DBContainer.postgresqlContainer.username
        val password = DBContainer.postgresqlContainer.password

        val conn = DriverManager.getConnection(jdbcURL, username, password)
        val resultSet = conn.createStatement().executeQuery("SELECT 1")
        resultSet.next()
        val result = resultSet.getInt(1)

        assertEquals(1, result)
    }
}