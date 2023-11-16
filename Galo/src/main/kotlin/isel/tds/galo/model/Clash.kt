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
    return ClashRun( storage, id, me, game.play(toPosition) ).also { this.game }
}