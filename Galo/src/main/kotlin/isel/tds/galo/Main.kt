package isel.tds.galo

import isel.tds.galo.model.Board
import isel.tds.galo.model.canPlay
import isel.tds.galo.model.play
import isel.tds.galo.view.readCommandLine
import isel.tds.galo.view.show
import java.lang.IllegalArgumentException

fun main() {
    var board: Board? = null
    //getCommands
    val commands = getCommands()
    while (true) {
        val (nameCmd, args) = readCommandLine()
        //obter o command para o name
        val cmd = commands[nameCmd]
        try {
            //...
            if (cmd!!.isToFinish) break
            board = cmd.execute(args, board)

        } catch (e: IllegalArgumentException) {
            println(e.message)
        } catch (e: IllegalStateException) {
            println(e.message)
        }
        board?.show()
    }
}






