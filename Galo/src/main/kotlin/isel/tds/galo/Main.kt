package isel.tds.galo

import isel.tds.galo.model.Board
import isel.tds.galo.model.canPlay
import isel.tds.galo.model.play
import isel.tds.galo.model.show

fun main() {
    var board: Board? = null
    while (true) {
        print(">")
        val cmd = readln().uppercase().split(" ")

        when (cmd[0]) {
            "NEW" -> board = Board()
            "EXIT" -> return
            "PLAY" -> {
                board?.let {
                    val playIdx = cmd[1].toInt()
                    if (it.canPlay(playIdx))
                        board = it.play(playIdx)
                    else
                        println("Invalid play...")
                }
            }
        }
        board?.show()
    }
}




