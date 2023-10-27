package isel.tds.galo

import isel.tds.galo.model.Board
import isel.tds.galo.model.play
import isel.tds.galo.model.toPosition

abstract class Command {
    open val isToFinish: Boolean = false
    open fun execute(args: List<String>, board: Board?): Board = throw IllegalStateException("Game over")

}

object PlayCommand : Command() {
    override fun execute(args: List<String>, board: Board?): Board {
        checkNotNull(board) { "Game not started" }

        val arg = requireNotNull(args.firstOrNull()) { "Missing index" }
        val playIdx = requireNotNull(arg.toIntOrNull()) { "Invalid index $arg" }
        return board.play(playIdx.toPosition())
    }
}

fun getCommands(): Map<String, Command> = mapOf(
    "NEW" to object : Command() {
        override fun execute(args: List<String>, board: Board?): Board = Board()
    },
    "EXIT" to object : Command() {
        override val isToFinish: Boolean = true
    },
    "PLAY" to PlayCommand

)