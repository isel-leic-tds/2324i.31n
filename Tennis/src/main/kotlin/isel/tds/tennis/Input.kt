package isel.tds.tennis

import isel.tds.tennis._01_singleClass.Player


fun readWinner(): Player {
    return Player.valueOf(readWinnerRecursive().toString())
}

private fun readWinnerIteractive(): Char {
    var winner: Char?
    do {
        println("Who was the winner ('A' or 'B')?")
        winner = readln().firstOrNull()?.uppercaseChar()
        //} while (winner != 'A' && winner != 'B')
    } while (winner == null || (winner != null && winner !in "AB"))

    return winner
}

private fun readWinnerRecursive(): Char {
    println("Who was the winner ('A' or 'B')?")
    return readln().firstOrNull()?.uppercaseChar()?.takeIf { it in "AB" } ?: readWinnerRecursive()
}