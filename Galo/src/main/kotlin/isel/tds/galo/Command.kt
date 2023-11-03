package isel.tds.galo

import isel.tds.galo.model.Game
import isel.tds.galo.model.newBoard
import isel.tds.galo.model.play
import isel.tds.galo.model.toPosition
import isel.tds.galo.storage.TextFileStorage
import isel.tds.galo.view.showScore

abstract class Command {
    open val isToFinish: Boolean = false
    open fun execute(args: List<String>, game: Game): Game = throw IllegalStateException("Game over")

}

object PlayCommand : Command() {
    override fun execute(args: List<String>, game: Game): Game {
        checkNotNull(game) { "Game not started" }

        val arg = requireNotNull(args.firstOrNull()) { "Missing index" }
        val playIdx = requireNotNull(arg.toIntOrNull()) { "Invalid index $arg" }
        return game.play(playIdx.toPosition())
    }
}

fun getCommands(storage: TextFileStorage<String, Game>): Map<String, Command> = mapOf(
    "NEW" to object : Command() {
        override fun execute(args: List<String>, game: Game): Game = game.newBoard()
    },
    "EXIT" to object : Command() {
        override val isToFinish: Boolean = true
    },
    "PLAY" to PlayCommand,
    "SAVE" to object : Command() {
        override fun execute(args: List<String>, game: Game): Game {
            require(args.isNotEmpty()) { "Missing name" }
            requireNotNull(game) { "Game not started" }
            val name = args[0]
            require(name.isNotEmpty()) { "Name must not be empty" }
            storage.create(name, game)
            return game
        }
    },
    "SCORE" to object: Command() {
        override fun execute(args: List<String>, game: Game): Game =
            game.also { it.showScore() }
    },
    "LOAD" to object : Command() {
        override fun execute(args: List<String>, game: Game): Game {
            val name = requireNotNull(args.firstOrNull()) { "Missing name" }
            return checkNotNull(storage.read(name)) { "Game $name not found" }
        }
    },

)