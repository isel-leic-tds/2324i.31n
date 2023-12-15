package isel.tds.galo.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import isel.tds.galo.model.*
import isel.tds.galo.mongo.MongoDriver
import isel.tds.galo.storage.GameSerializer
import isel.tds.galo.storage.MongoStorage

class AppViewModel(driver: MongoDriver) {



    private val storage = MongoStorage<String, Game>("games", driver, GameSerializer)
    private var clash by mutableStateOf( Clash(storage))

    val board: Board? get() = (clash as? ClashRun)?.game?.board
    val me: Player? get() = (clash as? ClashRun)?.me

    var viewScore by mutableStateOf(false)
        private set
    var inputType by mutableStateOf<InputType?>(null)
        private set

    var errorMessage by mutableStateOf<String?>(null) //ErrorDialog state
        private set

    val score: Score get() = (clash as ClashRun).game.score

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

    fun refreshGame() {
        try {
            clash = clash.refreshClash()
        } catch (e: Exception) {
            errorMessage = e.message
        }
    }

    fun newGame(gameName: String) {
        try {
            clash = clash.startClash(gameName)
            hideNewOrJoinGameDialog()
        } catch (e: Exception) {
            errorMessage = e.message
        }

    }
    fun joinGame(gameName: String) {
        try {
            clash = clash.joinClash(gameName)
            hideNewOrJoinGameDialog()
        } catch (e: Exception) {
            errorMessage = e.message
        }
    }

    enum class InputType(val txt: String)
    { NEW("Start"), JOIN("Join") }
}