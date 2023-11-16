package isel.tds.galo

import isel.tds.galo.model.*
import isel.tds.galo.storage.Storage
import isel.tds.galo.storage.TextFileStorage
import isel.tds.galo.view.showScore

class Command (
    val isToFinish: Boolean = false,
    val execute :(args: List<String>, clash: Clash)-> Clash =
        { _, _ -> throw IllegalStateException("Game over")}
)


fun playCommand() = Command(){ args, clash ->
        check(clash is ClashRun) { "Game not started" }

        val arg = requireNotNull(args.firstOrNull()) { "Missing index" }
        val playIdx = requireNotNull(arg.toIntOrNull()) { "Invalid index $arg" }
        clash.play(playIdx.toPosition())
    }


fun getCommands(storage: Storage<String, Game>): Map<String, Command> = mapOf(
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