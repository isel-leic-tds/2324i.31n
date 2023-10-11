package isel.tds.tennis

enum class Player {
    A, B
}

/**
 * 45 -> advantage
 * 60 -> game
 */
val validPoints = listOf(0, 15, 30, 40, 45, 60)

class Score(val pointsA: Int = 0, val pointsB: Int = 0) {

    init {
        require(pointsA in validPoints)
        require(pointsB in validPoints)

    }

    override fun toString(): String {
        return "A:" + pointsA + " B:" + pointsB
    }

    fun next(winner: Player): Score {
        println("got:" + winner)
        TODO()
    }
}

fun initialScore() = Score(0, 0)

fun Score.placard(): String = toString()