package isel.tds.galo

import isel.tds.galo.model.Clash
import isel.tds.galo.model.Game
import isel.tds.galo.mongo.MongoDriver
import isel.tds.galo.storage.GameSerializer
import isel.tds.galo.storage.MongoStorage
import isel.tds.galo.view.readCommandLine
import isel.tds.galo.view.show
import java.lang.IllegalArgumentException

fun main() {

    MongoDriver("galo").use{ driver ->
    //    val storage = TextFileStorage<String, Game>("saves", GameSerializer)
        val storage = MongoStorage<String, Game>("games", driver, GameSerializer)
        var clash = Clash(storage)
        val commands = getCommands(storage)
        while (true) {
            val (nameCmd, args) = readCommandLine()
            val cmd = commands[nameCmd]
            try {
                clash = cmd!!.execute(clash, args)
                if (cmd.isToFinish) break

            } catch (e: IllegalArgumentException) {
                println(e.message)
            } catch (e: IllegalStateException) {
                println(e.message)
            }
            clash.show()
        }
    }
}








