package isel.tds.galo.model

import isel.tds.galo.storage.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class Clash(val storage: Storage<String, Game>)

class ClashRun(
    storage: Storage<String, Game>,
    val id: String,
    val me: Player,
    val game: Game
): Clash(storage)

fun Clash.play(toPosition: Position): Clash {
    check( this is ClashRun ) { "Clash not started" }
    check( (game.board as BoardRun).turn == me ) { "Not your turn" }
    val clash = ClashRun( storage, id, me, game.play(toPosition) )
    storage.update(id, clash.game)
    return clash
}

fun Clash.deleteIfIsOwner() {
if (this is ClashRun && me==Player.X)
        storage.delete(id)
}

fun Clash.startClash(name: String): Clash {
    val game = Game(firstPlayer = Player.X).newBoard()
    storage.create(name, game)
    return ClashRun( storage, name, Player.X, game)
}

fun Clash.joinClash(name: String): Clash {
    val game = storage.read(name) ?: error("Clash $name not found")
    return ClashRun( storage, name, Player.O, game)
}

suspend fun Clash.refreshClash(): Clash {
    check( this is ClashRun ) { "Clash not started" }
    val gameAfter = storage.slowRead(id) ?: throw GameDeletedException()
//    val gameAfter = storage.read(id) ?: throw GameDeletedException()
    if (game.board == gameAfter.board) throw NoChangesException()
    return ClashRun( storage, id, me, gameAfter)
}


suspend fun Storage<String,Game>.slowRead(key: String): Game? {
    fun log(label: String) {
        println("$label: thread=${java.lang.Thread.currentThread().name} " +
                "time=${java.lang.System.currentTimeMillis()/1000}")
    }
    log("slowRead 1")
    val res = withContext(Dispatchers.IO){
        log("slowRead 2")
        java.lang.Thread.sleep(5000)
        log("slowRead 3")
        read(key)
    }
    log("slowRead 4")
    return res
}

class NoChangesException : IllegalStateException("No changes")
class GameDeletedException : IllegalStateException("Game deleted")


fun Clash.newBoard():Clash{
    check( this is ClashRun ) { "Clash not started" }
    val newGame = game.newBoard().also{
        storage.update(id,it)
    }
    return ClashRun(storage,id,me,newGame)
}

fun Clash.canNewBoard() = this is ClashRun && game.board is BoardWin