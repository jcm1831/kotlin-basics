package indigo

class CardDeck(private val cards: CardCollection = CardCollection()) {
    init {
        // define ranks
        val ranks =
            buildList {
                addAll(listOf("A", "J", "Q", "K"))
                addAll(listOf("2", "3", "4", "5", "6", "7", "8", "9", "10"))
            }
        // define suits
        val suits = listOf("\u2666", "\u2665", "\u2660", "\u2663")
        // create all cards in deck
        for (suit in suits) {
            for (rank in ranks) {
                cards.add(Card(rank, suit))
            }
        }
        // shuffle deck
        cards.shuffle()
    }

    fun dealCards(numCards: Int) = getCards(numCards)
    fun dealCards(players: Players) {
        if (cards.isNotEmpty() && players.haveEmptyHands()) {
            players.human.takeCardsOnHand(getCards())
            players.computer.takeCardsOnHand(getCards())
        }
    }
    private fun getCards(numCards: Int = 6): CardCollection {
        val cardsFromDeck = CardCollection()
        for (i in 0 until numCards) {
            cardsFromDeck.add(cards.first())
            cards.removeAt(0)
        }
        return cardsFromDeck
    }
    fun isEmpty() = cards.isEmpty()
}

