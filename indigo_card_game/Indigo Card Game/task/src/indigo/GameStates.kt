package indigo

enum class States {
    PLAYERS_TURN,
    COMPUTERS_TURN,
    GAME_OVER,
    NULL;
}

class GameStates(private var state : States = States.NULL) {
    fun initTurn(players: Players) {
        while (true) {
            println("Play first?")
            state =  when (readln().lowercase()) {
                "yes" -> {
                    players.setHumanPlayedFirst()
                    States.PLAYERS_TURN
                }
                "no" -> {
                    players.setComputerPlayedFirst()
                    States.COMPUTERS_TURN
                }
                else -> continue
            }
            break
        }
    }
    fun updateTurn() {
        state = if (isPlayersTurn()) States.COMPUTERS_TURN else States.PLAYERS_TURN
    }
    fun setGameOver() { state = States.GAME_OVER }
    fun isPlayersTurn() = state == States.PLAYERS_TURN
    fun isComputersTurn() = state == States.COMPUTERS_TURN
    fun isNotGameOver() = state != States.GAME_OVER
}

