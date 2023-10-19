package isel.tds.tennis._01_singleClass

import isel.tds.tennis.Player
import isel.tds.tennis.Points
import isel.tds.tennis.Points.*


class Score private constructor(val pointsA: Points, val pointsB: Points) {
    constructor () : this(LOVE, LOVE)

    override fun toString(): String {
        return "A:" + pointsA + " B:" + pointsB
    }

    fun next(winner: Player): Score = when {
        winner == Player.A && pointsB == ADVANTAGE -> Score(FORTY, FORTY)
        winner == Player.B && pointsA == ADVANTAGE -> Score(FORTY, FORTY)
        winner == Player.A && pointsA == FORTY && pointsB != FORTY -> Score(GAME, pointsB)
        winner == Player.B && pointsB == FORTY && pointsA != FORTY -> Score(pointsA, GAME)
        pointsA == GAME || pointsB == GAME -> error("Illegal state")
        else ->
            if (winner == Player.A) Score(pointsA.next(), pointsB)
            else Score(pointsA, pointsB.next())
    }

    val isGame get() = pointsA == GAME || pointsB == GAME
    val placard
        get() = when {
            pointsA == FORTY && pointsB == FORTY -> "Deuce"
            pointsA == ADVANTAGE -> "Advantage A"
            pointsB == ADVANTAGE -> "Advantage B"
            pointsA == GAME -> "Game A"
            pointsB == GAME -> "Game B"
            else -> "${pointsA.value} - ${pointsB.value}"
        }
}

fun initialScore() = Score()
