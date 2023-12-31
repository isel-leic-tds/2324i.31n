package isel.tds.galo.storage

import isel.tds.galo.model.*

object GameSerializer: Serializer<Game> {
    override fun serialize(data: Game) = buildString {
        appendLine( data.score.entries.joinToString(" ") { (plyr,pts) ->
            "$plyr=$pts"
        } )
        appendLine( data.firstPlayer )
        data.board?.let { appendLine(BoardSerializer.serialize(it)) }
    }
    override fun deserialize(text: String) =
        text.split("\n").let{ (score,player,board) -> Game(
            score = score.split(" ").map { it.split("=") }
                .associate { (plyr,pts) ->
                    plyr.toPlayerOrNull() to pts.toInt()
                },
            firstPlayer = player.toPlayer(),
            board = if (board.isBlank()) null
            else BoardSerializer.deserialize(board)
        ) }
}