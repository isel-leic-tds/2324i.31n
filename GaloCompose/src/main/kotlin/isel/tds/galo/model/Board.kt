package isel.tds.galo.model

import isel.tds.galo.model.Player.O
import isel.tds.galo.model.Player.X

const val BOARD_SIZE = 3
const val BOARD_CELLS = BOARD_SIZE * BOARD_SIZE

//class Board(
//    val cells: List<Player?> = listOf(
//        null, null, null,
//        null, null, null,
//        null, null, null
//    ),
//    val turn: Player = X,
//    val winner: Player? = null
//)
//
//fun Board.play(playIdx: Position): Board {
//    require(playIdx.index in 0..<BOARD_CELLS) { "Out of bounds" }
//    require(canPlay(playIdx)) { "Not a free position" }
//
//    val boardCellsAfterPlay = cells.mapIndexed({ idx, c -> if (idx == playIdx.index) turn else c })
//
//    return Board(
//        cells = boardCellsAfterPlay,
//        turn = turn.other,
//        winner = winner(playIdx, boardCellsAfterPlay)
//    )
//}
//
//
//fun Board.canPlay(playIdx: Position): Boolean = cells[playIdx.index] == null
//
//fun Board.isDraw(): Boolean = cells.all { it != null }
//        && winner == null

//fun Board.isWinner(player: Player): Boolean =
//// validate backslah \ -> 0, 4, 8
//    //  (cells[0]==player && cells[4]==player && cells[8]=player ) ||
//    (0..8 step 4).all { cells[it] == player } ||
//
//// validate forward slash / -> 2, 4, 6
//            (2..6 step 2).all { cells[it] == player } ||
//
//// validate rows -> 0,1,2 || 3,4,5 || 6,7,8
//            (0..6 step 3).any { row -> (row..row + 2).all { cells[it] == player } } ||
//// validate columns -> 0,3,6 || 1,4,7 || 2,5,8
//            (0..2).any { col -> (col..col + 6 step 3).all { cells[it] == player } }

//fun Board.winner(pos: Position, boardCellsAfterPlay: List<Player?>): Player? {
//    val player = checkNotNull(boardCellsAfterPlay[pos.index])
//    if (boardCellsAfterPlay.count { it == player } < BOARD_SIZE) return null
//    return player.takeIf {
//        //Validate Horizontal lines/Rows
//        // -> 0, 1, 2 || 3, 4, 5 || 6, 7, 8
//        (0..<BOARD_SIZE).all {
//            boardCellsAfterPlay[it + pos.row * BOARD_SIZE] == player
//        } ||
//        //Validate Vertical Columns
//        // -> 0, 3, 6 || 1, 4, 7 || 2, 5, 8
//        (0..<BOARD_CELLS step BOARD_SIZE).all {
//            boardCellsAfterPlay[it + pos.col] == player
//        } ||
//        // Validate \ -> idxs= 0, 4, 8, backslash
//        pos.backSlash && (0..<BOARD_CELLS step BOARD_SIZE + 1).all {
//            boardCellsAfterPlay[it] == player
//        } ||
//        // Validate / -> idxs=2, 4, 6, slash or forward slash
//        pos.slash && (BOARD_SIZE - 1..<BOARD_CELLS - 1 step BOARD_SIZE - 1).all {
//            boardCellsAfterPlay[it] == player
//        }
//    }
//}


typealias BoardCells = Map<Position,Player>
sealed class Board(val boardCells: BoardCells){
    override fun equals(other: Any?): Boolean {
        return other is Board && boardCells == other.boardCells
    }
}
class BoardRun(boardCells: BoardCells, val turn: Player): Board(boardCells){
    override fun equals(other: Any?): Boolean {
        return super.equals(other) && other is BoardRun && turn == other.turn
    }
}
class BoardWin(boardCells: BoardCells, val winner: Player): Board(boardCells) {
    override fun equals(other: Any?): Boolean {
        return super.equals(other) && other is BoardWin && winner == other.winner
    }
}
class BoardDraw(boardCells: BoardCells) : Board(boardCells)

fun Board(start: Player = Player.X): Board = BoardRun(emptyMap(),start)

fun Board.play(playPositionIdx: Position): Board = when(this){
    is BoardRun -> {
        require(boardCells[playPositionIdx] == null) { "Position $playPositionIdx used" }
        val boardCellsAfterPlay =
            boardCells + (playPositionIdx to turn)
        when {
            isWinner(playPositionIdx,boardCellsAfterPlay) -> BoardWin(boardCellsAfterPlay, turn)
            boardCellsAfterPlay.size == BOARD_CELLS -> BoardDraw(boardCellsAfterPlay)
            else -> BoardRun(boardCellsAfterPlay, turn.other)
        }
    }
    is BoardDraw, is BoardWin -> error("Game Over")
}

@OptIn(ExperimentalStdlibApi::class)
private fun isWinner(pos: Position, boardCells: BoardCells): Boolean =
    boardCells.size >= BOARD_SIZE*Player.entries.size-1 &&
            boardCells.filter { it.value == boardCells[pos] }.keys.run {
                count{ it.row == pos.row } == BOARD_SIZE ||
                count{ it.col == pos.col } == BOARD_SIZE ||
                count{ it.slash } == BOARD_SIZE ||
                count{ it.backSlash } == BOARD_SIZE
            }