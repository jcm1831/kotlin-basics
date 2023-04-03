package indigo

class ScoreBoard {
    fun calculateFinalScore(players: Players) {
        if(players.human.wonMoreCardsThan(players.computer)) {
            players.human.updateScore(3)
        } else if (players.computer.wonMoreCardsThan(players.human)) {
            players.computer.updateScore(3)
        } else {
            if (players.human.playedFirst) {
                players.human.updateScore(3)
            } else if (players.computer.playedFirst) {
                players.computer.updateScore(3)
            }
        }
    }
    fun printCurrentScore(players: Players) {
        val winner = if (players.human.wonLast) "Player" else "Computer"
        println("$winner wins cards")
        print(players)
    }
    fun printFinalScore(players: Players) = print(players)
    private fun print(players: Players) {
        println(
            "Score: Player ${players.human.score} - " +
                    "Computer ${players.computer.score}"
        )
        println(
            "Cards: Player ${players.human.cardsWon.number()} - " +
                    "Computer ${players.computer.cardsWon.number()}"
        )
    }
}