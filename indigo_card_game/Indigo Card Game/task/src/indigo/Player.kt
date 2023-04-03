package indigo

open class Player {
    val cardsOnHand = CardCollection()
    val cardsWon = CardCollection()
    var score = 0
    var wonLast = false
    var playedFirst = false

    open fun chooseCard(cardsOnTable: CardsOnTable): Card? = Card()
    fun playCard(gameState: GameStates, cardsOnTable: CardsOnTable) {
        val chosenCard = chooseCard(cardsOnTable)
        if (chosenCard != null) {
            cardsOnTable.add(chosenCard)
            cardsOnHand.removeAt(chosenCard)
        } else {
            gameState.setGameOver()
        }
    }

    fun updateScore(increment: Int = 0) {
        score = cardsWon.getScore() + increment
    }

    fun takeCardsOnHand(cards: CardCollection) = cardsOnHand.addAll(cards)
    fun takeCardsWon(cards: CardsOnTable) {
        cardsWon.addAll(cards.get())
        wonLast = true
    }

    open fun printCardsOnHand() {
        cardsOnHand.print()
        println()
    }

    fun hasEmptyHand() = cardsOnHand.isEmpty()
    fun wonMoreCardsThan(player: Player): Boolean {
        return cardsWon.number() > player.cardsWon.number()
    }
}

class HumanPlayer : Player() {
    override fun chooseCard(cardsOnTable: CardsOnTable): Card? {
        while (true) {
            print("Choose a card to play (1-${cardsOnHand.number()}): ")
            val input = readln()
            // check for exit
            return if (input == "exit") {
                null
            } else {
                val card = input.toIntOrNull()
                if (card != null && card in 1..cardsOnHand.number()) {
                    cardsOnHand[(card - 1)]
                } else {
                    continue
                }
            }
        }
    }

    override fun printCardsOnHand() {
        for (i in 0 until cardsOnHand.number()) {
            print("${i + 1})${cardsOnHand[i]} ")
        }
        println()
    }
}

class ComputerPlayer : Player() {
    override fun chooseCard(cardsOnTable: CardsOnTable): Card {
        if (cardsOnHand.number() == 1) {
            return cardsOnHand.first()
        } else if (cardsOnTable.isEmpty() || (cardsOnTable.isNotEmpty() && cardsOnHand.hasNoCandidateCard(
                cardsOnTable.topCard()
            ))
        ) {
            val sameSuit = cardsOnHand.sameSuitCards()
            if (sameSuit.number() >= 2) return sameSuit.randomCard()
            val sameRank = cardsOnHand.sameRankCards()
            if (sameRank.number() >= 2) return sameRank.randomCard()
            return cardsOnHand.randomCard()
        } else if (cardsOnHand.hasCandidateCard(cardsOnTable.topCard())) {
            val candidates = cardsOnHand.candidateCards(cardsOnTable.topCard())
            if (candidates.number() == 1) {
                return candidates.first()
            } else {
                val sameSuit = candidates.sameSuitCards(cardsOnTable.topCard())
                if (sameSuit.number() >= 2) return sameSuit.randomCard()
                val sameRank = candidates.sameRankCards(cardsOnTable.topCard())
                if (sameRank.number() >= 2) return sameRank.randomCard()
                return candidates.randomCard()
            }
        }
        return cardsOnHand.randomCard()
    }
}

class Players(
    val human: HumanPlayer = HumanPlayer(),
    val computer: ComputerPlayer = ComputerPlayer()
) {
    fun takeRemainingCards(cardsOnTable: CardsOnTable) {
        if (human.wonLast) {
            human.takeCardsWon(cardsOnTable)
        } else if (computer.wonLast) {
            computer.takeCardsWon(cardsOnTable)
        } else {
            if (human.playedFirst) {
                human.takeCardsWon(cardsOnTable)
            } else if (computer.playedFirst){
                computer.takeCardsWon(cardsOnTable)
            }
        }
    }

    fun playCard(gameState: GameStates, cardsOnTable: CardsOnTable) {
        if (gameState.isPlayersTurn()) {
            print("Cards in hand: ")
            human.printCardsOnHand()
            human.playCard(gameState, cardsOnTable)
        } else if (gameState.isComputersTurn()) {
            computer.printCardsOnHand()
            computer.playCard(gameState, cardsOnTable)
            println("Computer plays ${cardsOnTable.topCard()}")
        }
        if (gameState.isNotGameOver()) {
            if (cardsOnTable.isWin()) {
                if (gameState.isPlayersTurn()) {
                    human.takeCardsWon(cardsOnTable)
                    human.updateScore()
                    computer.wonLast = false
                } else if (gameState.isComputersTurn()) {
                    computer.takeCardsWon(cardsOnTable)
                    computer.updateScore()
                    human.wonLast = false
                }
            }
            gameState.updateTurn()
        }
    }

    fun haveEmptyHands(): Boolean {
        return human.hasEmptyHand() && computer.hasEmptyHand()
    }

    fun setHumanPlayedFirst() {
        human.playedFirst = true
        computer.playedFirst = false
    }

    fun setComputerPlayedFirst() {
        human.playedFirst = false
        computer.playedFirst = true
    }
}
