package isel.tds.galo.storage

import isel.tds.galo.model.*
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance.Lifecycle
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.deleteRecursively

@TestInstance(Lifecycle.PER_CLASS)
class TextFileStorageTest {

    @OptIn(ExperimentalPathApi::class)
    @AfterAll
    @BeforeAll
    fun setUp() {
        val path = Path("storage")
        path.deleteRecursively()
    }

    @Test
    fun create() {
        val textFileStorage = TextFileStorage<String, String>(
            "storage",
            object : Serializer<String> {
                override fun serialize(data: String): String = data
                override fun deserialize(text: String): String = text
            })

        textFileStorage.create("key1", "data1")

        val dataRead= textFileStorage.read("key1")
        assertEquals("data1", dataRead)
    }

    @Test
    fun createBoard() {
        val textFileStorage = TextFileStorage<String, Board>("storage", BoardSerializer)

        val sut = BoardRun(
            boardCells = mapOf(
                Position(0) to Player.X, Position(1) to Player.O, Position(2) to Player.O,
                Position(3) to Player.O, Position(4) to Player.O, Position(5) to Player.X,
                Position(7) to Player.X, Position(8) to Player.X
            ),
            turn = Player.O
        ).play(6.toPosition())

        textFileStorage.create("keyBoard1", sut)

        val dataRead= textFileStorage.read("keyBoard1")
        assertEquals(sut, dataRead)
    }
}