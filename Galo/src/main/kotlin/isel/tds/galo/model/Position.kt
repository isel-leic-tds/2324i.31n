package isel.tds.galo.model


object XPTO {
    val x = "bla bla"

    fun xxx() = "XXX"

}

@JvmInline
value class Position private constructor(val index: Int) {


    val row: Int get() = index / BOARD_SIZE
    val col: Int get() = index % BOARD_SIZE
    val backSlash: Boolean get() = row == col
    val slash: Boolean get() = row + col == BOARD_SIZE-1


    companion object {
        val values: List<Position> = List(BOARD_CELLS) { idx -> Position(idx) }

        operator fun invoke(index: Int): Position {
            require(index in 0..<BOARD_CELLS) { "Out of bounds" }

            return values[index]
        }
    }
}

fun Int.toPosition(): Position = Position(this)