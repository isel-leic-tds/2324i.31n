import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import isel.tds.galo.model.*
import isel.tds.galo.mongo.MongoDriver
import isel.tds.galo.viewmodel.AppViewModel
import isel.tds.galo.viewmodel.AppViewModel.InputType

val CELL_SIDE = 100.dp       // Size of each cell
val GRID_THICKNESS = 5.dp    // Thickness of grid lines
val BOARD_SIDE = CELL_SIDE * BOARD_SIZE + GRID_THICKNESS * (BOARD_SIZE-1)

@Composable
@Preview
fun FrameWindowScope.App(driver: MongoDriver, onExit: () -> Unit) {
    val vm = remember { AppViewModel(driver) }

    MenuBar {
        Menu("Game") {
            Item("New Game", onClick = vm::showNewGameDialog)
            Item("Join Game", onClick = vm::showJoinGameDialog)
            Item("Refresh", onClick = vm::refreshGame)
            Item("New Board", onClick = vm::newBoard)
            Item("Show Score", onClick = vm::showScore)
            Item("Exit", onClick = onExit)
        }
    }
    MaterialTheme {
        Column {
            BoardView(vm.board?.boardCells, onClick=vm::play)
            StatusBar(vm.board, vm.me)
        }
        if (vm.viewScore) ScoreDialog(vm.score, vm::hideScore)
        vm.inputType?.let {
            StartOrJoinDialog(
                type = it,
                onCancel = vm::cancelInput,
                onAction = if (it == InputType.NEW) vm::newGame else vm::joinGame
            )
        }
        vm.errorMessage?.let { ErrorDialog(it, onClose = vm::hideError) }
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DialogBase(
    title: String,
    onClose: ()->Unit,
    content: @Composable ()->Unit
) = AlertDialog(
    onDismissRequest = onClose,
    title = { Text(title, style = MaterialTheme.typography.h4) },
    text = content,
    confirmButton = { TextButton(onClick = onClose) { Text("Close") } }
)


@Composable
fun ErrorDialog(message: String, onClose: ()->Unit) =
    DialogBase("Error", onClose) {
        Text(message, style = MaterialTheme.typography.h6)
    }

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StartOrJoinDialog(
    type: InputType,
    onCancel: ()->Unit,
    onAction: (String)->Unit) {
    var name by remember { mutableStateOf("") }  // Name in edition
    AlertDialog(
        onDismissRequest = onCancel,
        title = {
            Text(
                text = "Name to ${type.txt}",
                style = MaterialTheme.typography.h5
            )
        },
        text = {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name of game") }
            )
        },
        confirmButton = {
            TextButton(enabled = true,//Name.isValid(name),
                onClick = { onAction(name)}//Name(name)) }
            ) { Text(type.txt) }
        },
        dismissButton = {
            TextButton(onClick = onCancel){ Text("cancel") }
        }
    )

}

@OptIn(ExperimentalMaterialApi::class, ExperimentalStdlibApi::class)
@Composable
fun ScoreDialog(score: Score, closeDialog: () -> Unit) = AlertDialog(
    onDismissRequest = closeDialog,
    confirmButton = { TextButton(onClick = closeDialog){Text("Close")} },
    text = {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Player.entries.forEach { player ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Cell(player, size = 20.dp)
                        Text(" - ${score[player]}", style = MaterialTheme.typography.h4)
                    }
                }
                Text("Draw - ${score[null]}", style = MaterialTheme.typography.h4)
            }
        }
    }
)

@Composable
fun StatusBar(board: Board?, me: Player?){
    Row(
        modifier = Modifier
            .background(Color.LightGray)
            .width(BOARD_SIDE)
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        me?.let{
            Text("You ", style = MaterialTheme.typography.h4)
            Cell(player = it, size = 50.dp, color = Color.LightGray)
            Spacer(Modifier.width(30.dp))
        }
        val (txt, player) = when(board){
            is BoardRun -> "Turn:" to board.turn
            is BoardWin -> "Winner:" to board.winner
            is BoardDraw -> "Draw" to null
            null -> "Game not started" to null
        }

        Text(txt, style = MaterialTheme.typography.h4)
        if(player != null)
            Cell(player, size = 50.dp, color = Color.LightGray)
    }
}

@Composable
fun BoardView(boardCells: BoardCells?,
              onClick: (Position)->Unit = { _ ->  }) {
    Column(
        modifier = Modifier
            .size(BOARD_SIDE)
            .background(color = Color.Black),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        repeat(BOARD_SIZE){row ->
            Row(
                modifier = Modifier.fillMaxWidth().height(CELL_SIDE),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(BOARD_SIZE){col ->
                    val pos = Position(row, col)
                    Cell( boardCells?.get(pos))
                    {
                        onClick(pos)
                    }
                }
            }
        }
    }
}

@Composable
fun Cell(
    player: Player?,
    size: Dp = CELL_SIDE,
    color: Color = Color.White,
    onClick: ()->Unit={}) {
    val modifier = Modifier
        .size(size)
        .background(color = color)

    if (player == null) {
        Box(modifier.clickable(onClick = onClick))
    } else {
        val filename = when (player) {
            Player.X -> "cross.png"
            Player.O -> "circle.png"
        }
        Image(
            painter = painterResource(filename),
            contentDescription = "Player $player",
            modifier = modifier
        )
    }
}

fun main() =
    MongoDriver("galo").use { driver ->
        application {
            Window(
                onCloseRequest = ::exitApplication,
                title = "Jogo do Galo",
                state = WindowState(size = DpSize.Unspecified)
            ) {
                App(driver, ::exitApplication)
            }
        }
    }

@Composable
@Preview
fun testPreview() {
    Column {
//        Cell(Player.X)
//        Cell(null)
//        Cell(Player.O)
        val board = Board()
            .play(1.toPosition())
            .play(3.toPosition())
            .play(5.toPosition())
        BoardView(board.boardCells)
    }
}