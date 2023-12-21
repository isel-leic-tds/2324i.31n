package isel.tds.galo

import isel.tds.galo.model.*
import isel.tds.galo.storage.Storage
import isel.tds.galo.storage.TextFileStorage
import isel.tds.galo.view.showScore

class Command (
    val isToFinish: Boolean = false,
    val execute :Clash.(args: List<String>)-> Clash =
        { _ -> throw IllegalStateException("Game over")}
)

fun playCommand() = Command{ args ->
        check(this is ClashRun) { "Game not started" }

        val arg = requireNotNull(args.firstOrNull()) { "Missing index" }
        val playIdx = requireNotNull(arg.toIntOrNull()) { "Invalid index $arg" }
        play(playIdx.toPosition())
    }


fun getCommands(storage: Storage<String, Game>): Map<String, Command> = mapOf(
    "EXIT" to Command(isToFinish=true) {
        _ ->
        this.also {
            it.deleteIfIsOwner()
        }
    },
    "PLAY" to playCommand(),
    "SCORE" to Command() {_ ->
        check(this is ClashRun) { "Game not started" }
        this.also { it.game.showScore() }
    },
    "CREATE" to Command() { args ->
        val name = requireNotNull(args.firstOrNull()) { "Missing name" }
        startClash(name)
    },
    "JOIN" to Command() { args ->
        val name = requireNotNull(args.firstOrNull()) { "Missing name" }
        joinClash(name)
    },
//    "REFRESH" to Command() { _ ->
//        check(this is ClashRun) { "Game not started" }
//        refreshClash()
//    }
)





