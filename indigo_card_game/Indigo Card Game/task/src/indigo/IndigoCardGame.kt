package indigo

class IndigoCardGame {
    private val deck = CardDeck()
    private val cardsOnTable = CardsOnTable()
    private val players = Players()
    private val gameState = GameStates()
    private val scoreBoard = ScoreBoard()
    init {
        println("Indigo Card Game")
        gameState.initTurn(players)
        cardsOnTable.init(deck)
        deck.dealCards(players)
    }
    fun play() {
        while (gameState.isNotGameOver()) {
            cardsOnTable.print()
            players.playCard(gameState, cardsOnTable)
            if (cardsOnTable.isWin()) {
                scoreBoard.printCurrentScore(players)
                cardsOnTable.clear()
            }
            deck.dealCards(players)
            if (deck.isEmpty() && players.haveEmptyHands()) {
                cardsOnTable.print()
                players.takeRemainingCards(cardsOnTable)
                scoreBoard.calculateFinalScore(players)
                scoreBoard.printFinalScore(players)
                gameState.setGameOver()
            }
        }
        println("Game Over.")
    }
}