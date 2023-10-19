package isel.tds.tennis._02_oo

import isel.tds.tennis.Player
import isel.tds.tennis.Points
import isel.tds.tennis.Points.*


interface Score {
    fun next(winner: Player): Score
    val isGame: Boolean
    val placard: String
}

class ByPoints(private val pointsA: Points, private val pointsB: Points) : Score {
    override fun next(winner: Player): Score = when {
        winner == Player.A && pointsA == FORTY -> Game(Player.A)
        winner == Player.B && pointsB == FORTY -> Game(Player.B)
        winner == Player.A && pointsA == THIRTY && pointsB == FORTY -> Deuce
        winner == Player.B && pointsA == FORTY && pointsB == THIRTY -> Deuce
        else ->
            if (winner == Player.A) ByPoints(pointsA.next(), pointsB)
            else ByPoints(pointsA, pointsB.next())
    }

    override val isGame: Boolean get() = false
    override val placard: String
        get() = "${pointsA.value} - ${pointsB.value}"

}

object Deuce : Score {
    override fun next(winner: Player): Score = Advantage(winner)

    override val isGame: Boolean get() = false
    override val placard: String get() = "Deuce"
}

class Advantage(private val player: Player) : Score {
    override fun next(winner: Player): Score = when {
        winner == player -> Game(winner)
        else -> Deuce
    }

    override val isGame: Boolean get() = false
    override val placard: String get() = "Advantage $player"

}

class Game(private val winner: Player) : Score {
    override fun next(winner: Player): Score = error("Illegal state")
    override val isGame: Boolean get() = true
    override val placard: String get() = "Game $winner"
}

fun initialScore() = ByPoints(LOVE, LOVE)


