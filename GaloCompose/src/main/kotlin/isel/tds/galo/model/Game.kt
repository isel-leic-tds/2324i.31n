package isel.tds.galo.model

typealias Score = Map<Player?,Int>
data class Game @OptIn(ExperimentalStdlibApi::class) constructor(
    val board: Board? = null,
    val firstPlayer: Player = Player.X,
    val score: Score = (Player.entries + null)
        .associateWith{0}
)

fun Game.play(pos: Position): Game {
    checkNotNull(board) { "Game not started" }
    val b = board.play(pos)
    return copy( board = b,
        score = when (b) {
            is BoardWin -> score.advance(b.winner)
            is BoardDraw -> score.advance(null)
            else -> score
        }
    )
}

private fun Map<Player?,Int>.advance(player: Player?) =
    this + (player to this[player]!! + 1)

fun Game.newBoard(): Game = Game(
    Board(start = firstPlayer),
    firstPlayer.other,
    if (board is BoardRun) score.advance(board.turn.other) else score
)