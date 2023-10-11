package isel.tds.tennis

import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class ScoreTest {
    @Test
    fun `creation test `() {
        assertFailsWith<IllegalArgumentException> { Score(5, 0) }

    }
}