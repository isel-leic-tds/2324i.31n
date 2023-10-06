package isel.tds

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

class StackNotEmptyTest {
    @Test
    fun `vararg creation test`() {
        var stk = stackOf("AA", "BB")

        assertFalse(stk.isEmpty())

    }
}