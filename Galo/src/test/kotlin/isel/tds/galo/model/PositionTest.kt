package isel.tds.galo.model

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class PositionTest {

    @Test
    fun testPosition() {
        val p1 = Position(1)
        val p21 = Position(2)
        val p22 = Position(2)

        assertEquals(1, p1.index)
    }
}