package isel.tds.galo.model

import isel.tds.galo.storage.MongoStorage
import isel.tds.galo.storage.Storage

open class Clash(val storage: Storage<String, Game>) {
}

class ClashRun(
    storage: Storage<String, Game>,
    val id: String,
    val me: Player,
    val game: Game
): Clash(storage)

fun ClashRun.play(toPosition: Position): Clash {
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

fun Clash.refreshClash(): Clash {
    check( this is ClashRun ) { "Clash not started" }
    val game = storage.read(id) ?: error("Clash $id not found")
    return ClashRun( storage, id, me, game)
}