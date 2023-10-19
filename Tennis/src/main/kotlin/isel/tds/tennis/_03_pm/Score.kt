package isel.tds.tennis._03_pm

import isel.tds.tennis.Player
import isel.tds.tennis.Points
import isel.tds.tennis.Points.*


sealed interface Score

private class ByPoints(val pointsA: Points, val pointsB: Points) : Score
private class Game(val winner: Player) : Score
private object Deuce : Score
private class Advantage(val player: Player) : Score

fun initialScore(): Score = ByPoints(LOVE, LOVE)

val Score.placard
    get() = when (this) {
        is ByPoints -> "${pointsA.value} - ${pointsB.value}"
        is Game -> "Game $winner"
        is Deuce -> "Deuce"
        is Advantage -> "Advantage $player"
    }

val Score.isGame get() = this is Game

private fun ByPoints.pointsOfWinner(win: Player): Points = when {
    win == Player.A -> pointsA
    else -> pointsB
}

private fun ByPoints.pointsOfLooser(win: Player): Points = when {
    win == Player.A -> pointsB
    else -> pointsA
}

fun Score.next(win: Player): Score = when (this) {
    is ByPoints -> when {
        pointsOfWinner(win) == THIRTY && pointsOfLooser(win) == FORTY -> Deuce
        pointsOfWinner(win) == FORTY -> Game(win)
        else ->
            if (win == Player.A) ByPoints(pointsA.next(), pointsB)
            else ByPoints(pointsA, pointsB.next())
    }

    is Game -> error("Illegal state")
    is Deuce -> Advantage(win)
    is Advantage -> if (win == player) Game(win) else Deuce
}