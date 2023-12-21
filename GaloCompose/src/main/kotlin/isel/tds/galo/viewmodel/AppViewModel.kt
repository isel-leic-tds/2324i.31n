package isel.tds.galo.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import isel.tds.galo.model.*
import isel.tds.galo.mongo.MongoDriver
import isel.tds.galo.storage.GameSerializer
import isel.tds.galo.storage.MongoStorage
import kotlinx.coroutines.*

class AppViewModel(driver: MongoDriver, val scope: CoroutineScope) {


    private val storage = MongoStorage<String, Game>("games", driver, GameSerializer)
    private var clash by mutableStateOf( Clash(storage))

    val board: Board? get() = (clash as? ClashRun)?.game?.board
    val me: Player? get() = (clash as? ClashRun)?.me
    val hasClash: Boolean get() = clash is ClashRun
    val newAvailable: Boolean get() = clash.canNewBoard()

    var viewScore by mutableStateOf(false)
        private set
    var inputType by mutableStateOf<InputType?>(null)
        private set

    var errorMessage by mutableStateOf<String?>(null) //ErrorDialog state
        private set

    val score: Score get() = (clash as ClashRun).game.score

    private var waitingJob by mutableStateOf<Job?>(null)
    val isWaiting: Boolean get() = waitingJob != null
    private val turnAvailable: Boolean
        get() = (board as? BoardRun)?.turn == me || newAvailable


    fun newBoard(){
        clash = clash.newBoard()
    }
    fun showScore(){ viewScore = true}
    fun hideScore(){ viewScore = false}

    fun hideError() { errorMessage = null }

    fun play(pos: Position){
        try {
            clash = clash.play(pos)
        } catch (e: Exception) {
            errorMessage = e.message
        }
        waitForOtherSide()
    }

    fun showNewGameDialog() {
        inputType = InputType.NEW
    }

    fun showJoinGameDialog() {
        inputType = InputType.JOIN
    }

    private fun hideNewOrJoinGameDialog(){
        inputType = null
    }

    fun cancelInput() {
        inputType = null
    }

    suspend fun refreshGame() {
        try {
            clash = clash.refreshClash()
        } catch (e: Exception) {
            errorMessage = e.message
        }
    }

    fun newGame(gameName: String) {
        cancelWaiting()
        try {
            clash = clash.startClash(gameName)
            hideNewOrJoinGameDialog()
        } catch (e: Exception) {
            errorMessage = e.message
        }

    }
    fun joinGame(gameName: String) {
        cancelWaiting()
        try {
            clash = clash.joinClash(gameName)
            hideNewOrJoinGameDialog()
        } catch (e: Exception) {
            errorMessage = e.message
        }
        waitForOtherSide()
    }

    enum class InputType(val txt: String)
    { NEW("Start"), JOIN("Join") }

    fun exit() {
        clash.deleteIfIsOwner()
        cancelWaiting()
    }

    private fun cancelWaiting() {
        waitingJob?.cancel()
        waitingJob = null
    }
    private fun waitForOtherSide() {
        if (turnAvailable) return
        waitingJob = scope.launch {
            do {
                delay(3000)
                try { clash = clash.refreshClash() }
                catch (e: NoChangesException) { /* Ignore */ }
                catch (e: Exception) {
                    errorMessage = e.message
                    if (e is GameDeletedException) clash = Clash(storage)
                }
            } while (!turnAvailable)
            waitingJob = null
        }
    }
}