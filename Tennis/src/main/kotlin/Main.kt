import isel.tds.tennis._01_singleClass.initialScore
import isel.tds.tennis.outputPlacard
import isel.tds.tennis.readWinner

fun main(args: Array<String>) {
//    println("winner read:"+ readWinnerRecursive())

    var score = initialScore()
    do{
        outputPlacard(score.placard)
        score = score.next(readWinner())
    } while (score.isGame)
    outputPlacard("Final result")
    outputPlacard(score.placard)
}




