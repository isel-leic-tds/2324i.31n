package isel.tds.galo.model

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class BoardTest {

    @Test
    fun `Test empty Board`() {
        val sut = Board()

        assertFalse(sut.isWinner('X'))
        assertFalse(sut.isWinner('O'))
        assertFalse(sut.isDraw())
    }

    @Test
    fun `Test Draw`() {
        val sut = Board(
            cells = listOf(
                'O', 'X', 'O',
                'X', 'O', 'X',
                'X', 'O', 'X'
            )
        )

        assertFalse(sut.isWinner('X'))
        assertFalse(sut.isWinner('O'))
        assertTrue(sut.isDraw())
    }

    @Test
    fun `Test backslash X win`() {
        val sut = Board(
            cells = listOf(
                'X', 'O', 'O',
                'O', 'X', 'X',
                'O', 'O', 'X'
            )
        )

        assertTrue(sut.isWinner('X'))
        assertFalse(sut.isWinner('O'))
        assertFalse(sut.isDraw())
    }

    @Test
    fun `Test backslash O win`() {
        val sut = Board(
            cells = listOf(
                'O', 'X', 'X',
                'O', 'O', 'X',
                'X', 'X', 'O'
            )
        )

        assertFalse(sut.isWinner('X'))
        assertTrue(sut.isWinner('O'))
        assertFalse(sut.isDraw())
    }

    @Test
    fun `Test forward slash O win`() {
        val sut = Board(
            cells = listOf(
                'X', 'O', 'O',
                'O', 'O', 'X',
                'O', 'X', 'X'
            )
        )

        assertFalse(sut.isWinner('X'))
        assertTrue(sut.isWinner('O'))
        assertFalse(sut.isDraw())
    }

    @Test
    fun `Test forward slash X win`() {
        val sut = Board(
            cells = listOf(
                'O', 'X', 'X',
                'O', 'X', 'X',
                'X', 'O', 'O'
            )
        )

        assertTrue(sut.isWinner('X'))
        assertFalse(sut.isWinner('O'))
        assertFalse(sut.isDraw())
    }

    @Test
    fun `Test Row 0 X win`() {
        val sut = Board(
            cells = listOf(
                'X', 'X', 'X',
                'O', 'O', 'X',
                'X', 'O', 'O'
            )
        )

        assertTrue(sut.isWinner('X'))
        assertFalse(sut.isWinner('O'))
        assertFalse(sut.isDraw())
    }

    @Test
    fun `Test Row 1 X win`() {
        val sut = Board(
            cells = listOf(
                'O', 'X', 'O',
                'X', 'X', 'X',
                'O', 'O', 'X'
            )
        )

        assertTrue(sut.isWinner('X'))
        assertFalse(sut.isWinner('O'))
        assertFalse(sut.isDraw())
    }

    @Test
    fun `Test Row 2 X win`() {
        val sut = Board(
            cells = listOf(
                'O', 'X', 'O',
                'X', 'O', 'X',
                'X', 'X', 'X'
            )
        )

        assertTrue(sut.isWinner('X'))
        assertFalse(sut.isWinner('O'))
        assertFalse(sut.isDraw())
    }


    @Test
    fun `Test Col 0 X win`() {
        val sut = Board(
            cells = listOf(
                'X', 'X', 'O',
                'X', 'O', 'X',
                'X', 'O', 'O'
            )
        )

        assertTrue(sut.isWinner('X'))
        assertFalse(sut.isWinner('O'))
        assertFalse(sut.isDraw())
    }

    @Test
    fun `Test Col 1 X win`() {
        val sut = Board(
            cells = listOf(
                'O', 'X', 'O',
                'X', 'X', 'O',
                'O', 'X', 'X'
            )
        )

        assertTrue(sut.isWinner('X'))
        assertFalse(sut.isWinner('O'))
        assertFalse(sut.isDraw())
    }

    @Test
    fun `Test Col 2 X win`() {
        val sut = Board(
            cells = listOf(
                'O', 'X', 'X',
                'X', 'O', 'X',
                'O', 'O', 'X'
            )
        )

        assertTrue(sut.isWinner('X'))
        assertFalse(sut.isWinner('O'))
        assertFalse(sut.isDraw())
    }

    @Test
    fun testAdd() {
        assertEquals(5, addOrSubtract(2, 3, true))
    }
}