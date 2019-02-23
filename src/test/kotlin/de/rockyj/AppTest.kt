package de.rockyj

import kotlin.test.Test
import kotlin.test.assertNotNull

class AppTest {
    @Test fun testAppHasAGreeting() {
        assertNotNull(App().greeting, "app should have a greeting")
    }
}
