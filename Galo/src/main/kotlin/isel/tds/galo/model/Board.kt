package isel.tds.galo.model

class Board(
    val cells: List<Char> = listOf(
        ' ', ' ', ' ',
        ' ', ' ', ' ',
        ' ', ' ', ' '
    ),
    val turn: Char = 'X'
)

fun Board.play(playIdx: Int): Board {
    require(playIdx >= 0 && playIdx < 9) { "Out of bounds" }
    require(canPlay(playIdx)) { "Not a free position" }


    return Board(
        cells.mapIndexed({ idx, c -> if (idx == playIdx) turn else c }),
        if (turn == 'X') 'O' else 'X'
    )
}


fun Board.canPlay(playIdx: Int): Boolean = cells[playIdx] == ' '

fun Board.isDraw(): Boolean = cells.all { it != ' ' }
        && !isWinner('X') && !isWinner('O')

fun Board.isWinner(player: Char): Boolean =
// validate backslah \ -> 0, 4, 8
    //  (cells[0]==player && cells[4]==player && cells[8]=player ) ||
    (0..8 step 4).all { cells[it] == player } ||

// validate forward slash / -> 2, 4, 6
            (2..6 step 2).all { cells[it] == player } ||

// validate rows -> 0,1,2 || 3,4,5 || 6,7,8
            (0..6 step 3).any { row -> (row..row + 2).all { cells[it] == player } } ||
// validate columns -> 0,3,6 || 1,4,7 || 2,5,8
            (0..2).any { col -> (col..col + 6 step 3).all { cells[it] == player } }


fun addOrSubtract(op1: Int, op2: Int, toAdd: Boolean): Int {
    if (toAdd)
        return op1 + op2
    else
        return op1 - op2
}