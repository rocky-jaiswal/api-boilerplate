package de.rockyj.repositories

import de.rockyj.utils.DBExtension
import de.rockyj.utils.DBTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(DBExtension::class)
class UserRepositoryTest: DBTest() {

    @Test
    fun `Basic DB Select`() {
        val jdbi = DB(super.dataSource)
        val users = UserRepository(jdbi).getAllUsers()
        Assertions.assertEquals(0, users.size)
    }

    @Test
    fun `Basic DB Insert`() {
        val jdbi = DB(super.dataSource)
        UserRepository(jdbi).insertUser("foobar")

        val users = UserRepository(jdbi).getAllUsers()
        Assertions.assertEquals("foobar", users.first().name)
    }
}