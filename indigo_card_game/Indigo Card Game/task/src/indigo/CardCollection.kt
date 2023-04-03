package indigo

data class Card(val rank: String = "", val suit: String = "") {
    override fun toString(): String = rank + suit
    fun isCandidate(other: Card) = hasSameRank(other) || hasSameSuit(other)
    fun hasSameSuit(other: Card) = (suit == other.suit)
    fun hasSameRank(other: Card) = (rank == other.rank)
    fun getPoints(): Int {
        return when (rank) {
            "A", "10", "J", "Q", "K" -> 1
            else -> 0
        }
    }
}

class CardCollection(private val _cards: MutableList<Card> = mutableListOf()) {
    operator fun get(index: Int) = _cards[index]
    operator fun iterator() = _cards.iterator()
    fun add(card: Card) = _cards.add(card)
    fun addAll(cards: CardCollection) = _cards.addAll(cards._cards)
    fun removeAt(index: Int) = _cards.removeAt(index)
    fun removeAt(card: Card) = _cards.remove(card)
    fun clear() = _cards.clear()
    fun shuffle() = _cards.shuffle()
    fun first() = _cards.first()
    fun last() = _cards.last()
    fun number() = _cards.size
    fun isEmpty() = _cards.isEmpty()
    fun isNotEmpty() = _cards.isNotEmpty()
    fun print() = print(_cards.joinToString(" "))
    fun getScore(): Int {
        return _cards.fold(0) { sum, card -> sum + card.getPoints() }
    }

    fun hasCandidateCard(topCard: Card): Boolean {
        return _cards.any { card -> card.isCandidate(topCard) }
    }

    fun hasNoCandidateCard(topCard: Card) = !hasCandidateCard(topCard)
    fun candidateCards(topCard: Card): CardCollection {
        val candidates = CardCollection()
        for (card in _cards) {
            if (card.isCandidate(topCard)) {
                candidates.add(card)
            }
        }
        return candidates
    }

    fun sameSuitCards(topCard: Card): CardCollection {
        return CardCollection(_cards.filter { card -> card.hasSameSuit(topCard) }
            .toMutableList())
    }

    fun sameRankCards(topCard: Card): CardCollection {
        return CardCollection(_cards.filter { card -> card.hasSameRank(topCard) }
            .toMutableList())
    }

    fun sameSuitCards(): CardCollection {
        val sameSuit = mutableListOf<Card>()
        for (i in _cards.indices) {
            for (j in i+1 until number()) {
                if (_cards[i].hasSameSuit(_cards[j])) {
                    sameSuit.add(_cards[i])
                    sameSuit.add(_cards[j])
                }
            }
        }
        return CardCollection(sameSuit.distinct().toMutableList())
    }

    fun sameRankCards(): CardCollection {
        val sameRank = mutableListOf<Card>()
        for (i in _cards.indices) {
            for (j in i+1 until number()) {
                if (_cards[i].hasSameRank(_cards[j])) {
                    sameRank.add(_cards[i])
                    sameRank.add(_cards[j])
                }
            }
        }
        return CardCollection(sameRank.distinct().toMutableList())
    }

    fun randomCard() = _cards.random()
}