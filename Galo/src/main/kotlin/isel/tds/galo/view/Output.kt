package isel.tds.galo.view

import isel.tds.galo.model.*
import isel.tds.galo.model.Player.O
import isel.tds.galo.model.Player.X



private val separator = "---+".repeat(BOARD_SIZE-1)+"---"

fun Board.show() {
    //    println("  ${cells[0]} | ${cells[1]} | ${cells[2]}  ")
    //    println(" ---+---+--- ")
    //    println("  ${cells[3]} | ${cells[4]} | ${cells[5]} ")
    //    println(" ---+---+--- ")
    //    println("  ${cells[6]} | ${cells[7]} | ${cells[8]} ")

//    for (idx in 0..6 step 3) {
//        println("  ${cells.subList(idx, idx + 3).joinToString(" | ")}")
//        if (idx < 6)
//            println(" ---+---+--- ")
//    }
    Position.values.forEach { pos ->
        print(" ${boardCells[pos] ?: ' '} ")
        if (pos.col == BOARD_SIZE - 1) {
            println()
            if (pos.row < BOARD_SIZE -1) println(separator)
        } else print("|")
    }

    when (this){
        is BoardRun -> println("turn: $turn")
        is BoardWin -> println("Winner $winner")
        is BoardDraw -> println("Draw")
    }
}