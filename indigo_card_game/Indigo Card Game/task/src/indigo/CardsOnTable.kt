package indigo

class CardsOnTable(private var cards : CardCollection = CardCollection(),
                   private var lastTopCard : Card = Card()
) {
    fun init(deck: CardDeck, numCards: Int = 4) {
        print("Initial cards on the table: ")
        cards.addAll(deck.dealCards(numCards))
        cards.print()
    }
    fun print() {
        println()
        println(if (cards.isEmpty()) {
            "No cards on the table"
        } else {
            "${cards.number()} cards on the table, and the top card is ${topCard()}"
        })
    }
    fun add(card: Card) {
        if(cards.isNotEmpty()) lastTopCard = cards.last()
        cards.add(card)
    }
    fun clear() {
        cards.clear()
        lastTopCard = Card()
    }
    fun isWin() = topCard().isCandidate(lastTopCard)
    fun get() = cards
    fun topCard() = cards.last()
    fun isEmpty() = cards.isEmpty()
    fun isNotEmpty() = cards.isNotEmpty()
}