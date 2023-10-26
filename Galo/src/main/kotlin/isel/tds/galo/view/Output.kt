package isel.tds.galo.view

import isel.tds.galo.model.Board
import isel.tds.galo.model.isDraw
import isel.tds.galo.model.isWinner

fun Board.show() {
    //    println("  ${cells[0]} | ${cells[1]} | ${cells[2]}  ")
    //    println(" ---+---+--- ")
    //    println("  ${cells[3]} | ${cells[4]} | ${cells[5]} ")
    //    println(" ---+---+--- ")
    //    println("  ${cells[6]} | ${cells[7]} | ${cells[8]} ")

    for (idx in 0..6 step 3) {
        println("  ${cells.subList(idx, idx + 3).joinToString(" | ")}")
        if (idx < 6)
            println(" ---+---+--- ")
    }
    when {
        isWinner('X') -> println("Winner X")
        isWinner('O') -> println("Winner O")
        isDraw() -> println("Draw")
        else -> println("turn: $turn")
    }
}