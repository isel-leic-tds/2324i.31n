package isel.tds

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StackEmptyTest {
    @Test
    fun `creation test`() {
        var sut = stack<String>().push("A")
        assertEquals("A", sut.top)
    }
}