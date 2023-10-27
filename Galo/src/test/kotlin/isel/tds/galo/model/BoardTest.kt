package isel.tds.galo.model

import isel.tds.galo.model.Player.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class BoardTest {

    @Test
    fun `Test empty Board`() {
        val sut = Board()

        assertTrue(sut is BoardRun)
        assertTrue(sut is BoardRun && sut.turn == X)
//        assertFalse(sut.winner == X)
//        assertFalse(sut.winner == O)
//        assertFalse(sut.isDraw())
    }

    @Test
    fun `Test Draw`() {
        val sut = BoardRun(
            boardCells = mapOf(
                Position(0) to O, Position(1) to X, Position(2) to O,
                Position(3) to X, Position(4) to O, Position(5) to X,
                Position(6) to X, Position(7) to O
            ),
            turn = X
        ).play(8.toPosition())

        assertTrue( sut is BoardDraw)
//        assertFalse(sut.winner == X)
//        assertFalse(sut.winner == O)
//        assertTrue(sut.isDraw())
    }

    @Test
    fun `Test backslash X win`() {
        val sut = BoardRun(
            boardCells = mapOf(
                Position(0) to X, Position(1) to O, Position(2) to O,
                Position(3) to O, Position(4) to X, Position(5) to X,
                Position(6) to O, Position(7) to O
            ),
            turn = X
        ).play(8.toPosition())

        assertTrue(sut is BoardWin)
        assertTrue(sut is BoardWin && sut.winner == X)
//        assertFalse(sut.winner == O)
//        assertFalse(sut.isDraw())
    }

    @Test
    fun `Test backslash O win`() {
        val sut = BoardRun(
            boardCells = mapOf(
                Position(0) to O, Position(1) to X, Position(2) to X,
                Position(3) to O, Position(5) to X,
                Position(6) to X, Position(7) to X, Position(8) to O
            ),
            turn = O
        ).play(4.toPosition())

        assertTrue(sut is BoardWin)
        assertTrue(sut is BoardWin && sut.winner == O)
//        assertFalse(sut.winner == X)
//        assertTrue(sut.winner == O)
//        assertFalse(sut.isDraw())
    }

    @Test
    fun `Test forward slash O win`() {
        val sut = BoardRun(
            boardCells = mapOf(
                Position(0) to X, Position(1) to O, Position(2) to O,
                Position(3) to O, Position(4) to O, Position(5) to X,
                Position(7) to X, Position(8) to X
            ),
            turn = O
        ).play(6.toPosition())

        assertTrue(sut is BoardWin)
        assertTrue(sut is BoardWin && sut.winner == O)
//        assertFalse(sut.winner == X)
//        assertTrue(sut.winner == O)
//        assertFalse(sut.isDraw())
    }

    @Test
    fun `Test forward slash X win`() {
        val sut = BoardRun(
            boardCells = mapOf(
                Position(0) to O, Position(1) to X, Position(2) to X,
                Position(3) to O, Position(4) to X, Position(5) to X,
                Position(7) to O, Position(8) to O
            ),
            turn = X
        ).play(6.toPosition())

        assertTrue(sut is BoardWin)
        assertTrue(sut is BoardWin && sut.winner == X)
//        assertTrue(sut.winner == X)
//        assertFalse(sut.winner == O)
//        assertFalse(sut.isDraw())
    }

    @Test
    fun `Test Row 0 X win`() {
        val sut = BoardRun(
            boardCells = mapOf(
                Position(0) to X, Position(1) to X,
                Position(3) to O, Position(4) to O, Position(5) to X,
                Position(6) to X, Position(7) to O, Position(8) to O
            ),
            turn = X
        ).play(2.toPosition())

        assertTrue(sut is BoardWin)
        assertTrue(sut is BoardWin && sut.winner == X)
//        assertTrue(sut.winner == X)
//        assertFalse(sut.winner == O)
//        assertFalse(sut.isDraw())
    }

    @Test
    fun `Test Row 1 X win`() {
        val sut = BoardRun(
            boardCells = mapOf(
                Position(0) to O, Position(1) to X, Position(2) to O,
                Position(3) to X, Position(5) to X,
                Position(6) to O, Position(7) to O, Position(8) to X
            ),
            turn = X
        ).play(4.toPosition())

        assertTrue(sut is BoardWin)
        assertTrue(sut is BoardWin && sut.winner == X)
//        assertTrue(sut.winner == X)
//        assertFalse(sut.winner == O)
//        assertFalse(sut.isDraw())
    }

    @Test
    fun `Test Row 2 X win`() {
        val sut = BoardRun(
            boardCells = mapOf(
                Position(0) to O, Position(1) to X, Position(2) to O,
                Position(3) to X, Position(4) to O, Position(5) to X,
                Position(6) to X, Position(8) to X
            ),
            turn = X
        ).play(7.toPosition())

        assertTrue(sut is BoardWin)
        assertTrue(sut is BoardWin && sut.winner == X)
//        assertTrue(sut.winner == X)
//        assertFalse(sut.winner == O)
//        assertFalse(sut.isDraw())
    }


    @Test
    fun `Test Col 0 X win`() {
        val sut = BoardRun(
            boardCells = mapOf(
                Position(0) to X, Position(1) to X, Position(2) to O,
                Position(3) to X, Position(4) to O, Position(5) to X,
                Position(7) to O, Position(8) to O
            ),
            turn = X
        ).play(6.toPosition())

        assertTrue(sut is BoardWin)
        assertTrue(sut is BoardWin && sut.winner == X)
//        assertTrue(sut.winner == X)
//        assertFalse(sut.winner == O)
//        assertFalse(sut.isDraw())
    }

    @Test
    fun `Test Col 1 X win`() {
        val sut = BoardRun(
            boardCells = mapOf(
                Position(0) to O, Position(1) to X, Position(2) to O,
                Position(3) to X, Position(4) to X, Position(5) to O,
                Position(6) to O, Position(8) to X
            ),
            turn = X
        ).play(7.toPosition())

        assertTrue(sut is BoardWin)
        assertTrue(sut is BoardWin && sut.winner == X)
//        assertTrue(sut.winner == X)
//        assertFalse(sut.winner == O)
//        assertFalse(sut.isDraw())
    }

    @Test
    fun `Test Col 2 X win`() {
        val sut = BoardRun(
            boardCells = mapOf(
                Position(0) to O, Position(1) to X, Position(2) to X,
                Position(3) to X, Position(4) to O, Position(5) to X,
                Position(6) to O, Position(7) to O
            ),
            turn = X
        ).play(8.toPosition())

        assertTrue(sut is BoardWin)
        assertTrue(sut is BoardWin && sut.winner == X)
//        assertTrue(sut.winner == X)
//        assertFalse(sut.winner == O)
//        assertFalse(sut.isDraw())
    }
}