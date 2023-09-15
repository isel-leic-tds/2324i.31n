import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import java.lang.IllegalArgumentException
import kotlin.test.assertFails
import kotlin.test.assertFailsWith


@TestInstance(Lifecycle.PER_CLASS)
class DateTest {

    @Test
    fun createDate() {
        val sut = Date(2023, 9, 15)

        assertEquals(2023, sut.year)
        assertEquals(9, sut.month)
        assertEquals(15, sut.day)
    }


    @Test
    fun createDateWithDefaultDay() {
        val sut = Date(2023, 9)

        assertEquals(2023, sut.year)
        assertEquals(9, sut.month)
        assertEquals(1, sut.day)
    }

    @Test
    fun createDateWithDefaultDayAndMonth() {
        val sut = Date(2023)

        assertEquals(2023, sut.year)
        assertEquals(1, sut.month)
        assertEquals(1, sut.day)
    }

    @Test
    fun createDateWithDefaultMonth() {
        val sut = Date(2023, day = 12)

        assertEquals(2023, sut.year)
        assertEquals(1, sut.month)
        assertEquals(12, sut.day)
    }

    @Test
    fun `test non leap year`() {
        val sut = Date(2023, 9, 12)

        assertFalse(sut.leapYear)
    }

    @Test
    fun `test leap year`() {
        val sut = Date(2020, 9, 12)

        assertTrue(sut.leapYear)
        assertTrue(sut.leapYear2())
        assertTrue(sut.leapYear3)
        assertTrue(sut.leapYear4())
    }

    @Test
    fun `test last day of month`() {
        val sut = Date(2023, 9, 12)
        assertEquals(30, sut.lastDayOfMonth)
    }
    @Test
    fun `test last day of month of Feb with leap year`() {
        val sut = Date(2020, 2, 12)
        assertEquals(29, sut.lastDayOfMonth)
    }
    @Test
    fun `test last day of month of Feb with non leap year`() {
        val sut = Date(2023, 2, 12)
        assertEquals(28, sut.lastDayOfMonth)
    }

    @Test
    fun `test invalid year`() {
        assertFailsWith<IllegalArgumentException> { Date(120, 12) }
    }
    @Test
    fun `test invalid month`() {
        assertFailsWith<IllegalArgumentException> { Date(2020, 13) }
    }
    @Test
    fun `test invalid day leap year`() {
        assertFailsWith<IllegalArgumentException> { Date(2020, 2, 30) }
    }
    @Test
    fun `test invalid day non leap year`() {
        assertFailsWith<IllegalArgumentException> { Date(2023, 2, 29) }
    }
}


