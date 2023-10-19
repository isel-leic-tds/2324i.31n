package isel.tds.tennis

//import isel.tds.tennis.Player
//import isel.tds.tennis._01_singleClass.initialScore
//import isel.tds.tennis._02_oo.Player
//import isel.tds.tennis._02_oo.initialScore
//import isel.tds.tennis._03_pm.initialScore
//import isel.tds.tennis._03_pm.isGame
//import isel.tds.tennis._03_pm.next
//import isel.tds.tennis._03_pm.placard
import isel.tds.tennis._04_fp.initialScore
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.lang.IllegalStateException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ScoreTest {
    @Test
    fun `Creation test `() {
        val score = initialScore()
        assertEquals("0 - 0", score.placard)
        assertFalse(score.isGame)
    }

    @Test
    fun `Initial next test `() {
        val score = initialScore().next(Player.A)
        assertEquals("15 - 0", score.placard)
        assertFalse(score.isGame)

        val score2 = initialScore().next(Player.B)
        assertEquals("0 - 15", score2.placard)
        assertFalse(score2.isGame)
    }

    @Test
    fun `Next until 40 test `() {
        val score = initialScore()
            .next(Player.A).next(Player.A).next(Player.A)
            .next(Player.B)
        assertEquals("40 - 15", score.placard)
        assertFalse(score.isGame)
    }

    @Test
    fun `Deuce test`() {
        val score = initialScore()
            .next(Player.A).next(Player.A).next(Player.A)
            .next(Player.B).next(Player.B).next(Player.B)
        assertEquals("Deuce", score.placard)
        assertFalse(score.isGame)
    }

    @Test
    fun `Advantage A test`() {
        val score = initialScore()
            .next(Player.A).next(Player.A).next(Player.A)
            .next(Player.B).next(Player.B).next(Player.B)
            .next(Player.A)
        assertEquals("Advantage A", score.placard)
        assertFalse(score.isGame)
    }

    @Test
    fun `Deuce after Advantage test`() {
        val score = initialScore()
            .next(Player.A).next(Player.A).next(Player.A)
            .next(Player.B).next(Player.B).next(Player.B)
            .next(Player.A).next(Player.B)
        assertEquals("Deuce", score.placard)
        assertFalse(score.isGame)
    }

    @Test
    fun `Game after Advantage test`() {
        val score = initialScore()
            .next(Player.A).next(Player.A).next(Player.A)
            .next(Player.B).next(Player.B).next(Player.B)
            .next(Player.A).next(Player.A)
        assertEquals("Game A", score.placard)
        assertTrue(score.isGame)
    }

    @Test
    fun `Game after 40 test`() {
        val score = initialScore()
            .next(Player.A).next(Player.A).next(Player.A)
            .next(Player.B).next(Player.B)
            .next(Player.A)
        assertEquals("Game A", score.placard)
        assertTrue(score.isGame)
    }

    @Test
    fun `Illegal State Game after Game test`() {
        val score = initialScore()
            .next(Player.A).next(Player.A).next(Player.A)
            .next(Player.B).next(Player.B)
            .next(Player.A)
        assertFailsWith<IllegalStateException> { score.next(Player.A) }
        assertTrue(score.isGame)
    }
}