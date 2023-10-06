package isel.tds

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class StackEmptyTest {
    @Test
    fun `creation test`() {
        var sut = stack<String>().push("A")
        assertEquals("A", sut.top)
    }

    @Test
    fun `singleton creation test`() {
        var empty1 = stack<String>()
        var empty2 = stack<String>()
        assertTrue(empty1 === empty2)

        var empty11 = stack<String>()
        var empty22 = stack<Int>()
        assertTrue(empty11 === empty22)
    }
}