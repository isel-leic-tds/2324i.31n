package isel.tds.galo

import isel.tds.galo.model.Board
import isel.tds.galo.model.Game
import isel.tds.galo.mongo.MongoDriver
import isel.tds.galo.storage.BoardSerializer
import isel.tds.galo.storage.GameSerializer
import isel.tds.galo.storage.MongoStorage
import isel.tds.galo.storage.TextFileStorage
import isel.tds.galo.view.readCommandLine
import isel.tds.galo.view.show
import java.lang.IllegalArgumentException

fun main() {

    MongoDriver("galo").use{ driver ->
        var game = Game()

    //    val storage = TextFileStorage<String, Game>("saves", GameSerializer)
        val storage = MongoStorage<String, Game>("saves", driver, GameSerializer)

        val commands = getCommands(storage)
        while (true) {
            val (nameCmd, args) = readCommandLine()
            val cmd = commands[nameCmd]
            try {
                if (cmd!!.isToFinish) break
                game = cmd.execute(args, game)

            } catch (e: IllegalArgumentException) {
                println(e.message)
            } catch (e: IllegalStateException) {
                println(e.message)
            }
            game.show()
        }
    }
}






