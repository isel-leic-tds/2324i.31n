package isel.tds.tennis._04_fp

import isel.tds.tennis.Player
import isel.tds.tennis.Points
import isel.tds.tennis.Points.*
import isel.tds.tennis._02_oo.ByPoints


class Score(
    val placard: String,
    val isGame: Boolean = false,
    val next: (win: Player) -> Score
)

private fun game(winner: Player): Score = Score(
    placard = "Game $winner",
    isGame = true,
    next = { error("Illegal state") }
)

private fun deuce() = Score("Deuce", next = ::advantage)

private fun advantage(player: Player): Score = Score(
    placard = "Advantage $player",
    next = {
        if (it == player) game(it)
        else deuce()
    }
)


private fun byPoints(pointsA: Points, pointsB: Points): Score {

    fun pointsOfWinner(win: Player): Points = when {
        win == Player.A -> pointsA
        else -> pointsB
    }

    fun pointsOfLooser(win: Player): Points = when {
        win == Player.A -> pointsB
        else -> pointsA
    }

    return Score(
        placard = "${pointsA.value} - ${pointsB.value}",
        next = {
            when {
                pointsOfWinner(it) == FORTY -> game(it)
                pointsOfWinner(it) == THIRTY && pointsOfLooser(it) == FORTY -> deuce()
                else -> if (it == Player.A) byPoints(pointsA.next(), pointsB)
                else byPoints(pointsA, pointsB.next())
            }
        }
    )


}

fun initialScore(): Score = byPoints(LOVE, LOVE)
