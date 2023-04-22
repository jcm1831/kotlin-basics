package flashcards

import java.io.File

data class Term(val value: String = "") {
    override fun toString() = value
    fun print() = Logger.printlnAndLog("Print the definition of \"$value\":")
}

data class Definition(val value: String = "") {
    override fun toString() = value
}

data class Mistakes(val value: Int = 0) : Comparable<Mistakes> {
    override fun compareTo(other: Mistakes) =
        compareValuesBy(this, other) { it.value }

    override fun toString() = value.toString()
    operator fun plus(increment: Int): Mistakes {
        return Mistakes(value + increment)
    }
}

class FlashcardCollection {
    private val flashcards = mutableMapOf<Term, Definition>()
    private val statistics = mutableMapOf<Term, Mistakes>()

    init {
        val importFile = FileHandler.createImportFile()
        if (importFile != null) importCardsImpl(importFile)
    }

    fun addCard() {
        Logger.printlnAndLog("The card:")
        val term = Term(Logger.readlnAndLog())
        if (flashcards.containsKey(term)) {
            Logger.printlnAndLog("The card \"$term\" already exists.")
            return
        }
        Logger.printlnAndLog("The definition of the card:")
        val definition = Definition(Logger.readlnAndLog())
        if (flashcards.containsValue(definition)) {
            Logger.printlnAndLog("The definition \"$definition\" already exists.")
            return
        }
        flashcards[term] = definition
        statistics[term] = Mistakes()
        Logger.printlnAndLog("The pair (\"$term\":\"$definition\") has been added.")
    }

    fun removeCard() {
        Logger.printlnAndLog("Which card?")
        val term = Term(Logger.readlnAndLog())
        if (flashcards.containsKey(term)) {
            flashcards.remove(term)
            statistics.remove(term)
            Logger.printlnAndLog("The card has been removed.")
        } else {
            Logger.printlnAndLog("Can't remove \"$term\": there is no such card.")
        }
    }

    fun importCards() {
        val importFile = FileHandler.createImportFileFromInput()
        if (importFile != null) {
            importCardsImpl(importFile)
        } else {
            Logger.printlnAndLog("File not found.")
        }
    }

    fun exportCards() = exportCardsImpl(FileHandler.createExportFileFromInput())
    fun exportCards(exportFile: File) = exportCardsImpl(exportFile)

    fun askForDefinitions() {
        Logger.printlnAndLog("How many times to ask?")
        val timesToAsk = Logger.readlnAndLog().toInt()
        repeat(timesToAsk) {
            val randomTerm = flashcards.keys.random()
            randomTerm.print()
            val answer = Definition(Logger.readlnAndLog())
            checkAnswer(Pair(randomTerm, flashcards[randomTerm]!!), answer)
            Logger.printlnAndLog()
        }
    }

    fun getHardestCard() {
        val maxValue = statistics.values.maxOrNull()
        val maxEntries = statistics.filterValues { it == maxValue }
        val terms = maxEntries.keys.joinToString(", ") { "\"" + it + "\"" }
        if (maxValue != null && maxValue > Mistakes(0)) {
            Logger.printlnAndLog(
                "The hardest ${
                    if (maxEntries.size == 1) "card is" else "cards " +
                            "are"
                } $terms. You have $maxValue errors " +
                        "answering ${if (maxEntries.size == 1) "it" else "them"}."
            )
        } else {
            Logger.printlnAndLog("There are no cards with errors.")
        }
    }

    fun resetStatistics() {
        for ((term, _) in statistics) statistics.replace(term, Mistakes(0))
        Logger.printlnAndLog("Card statistics have been reset.")
    }

    private fun importCardsImpl(importFile: File) {
        val cards = importFile.readLines()
        for (card in cards) {
            val (term, definition, mistakes) = card.split(":")
            flashcards[Term(term)] = Definition(definition)
            statistics[Term(term)] = Mistakes(mistakes.toInt())
        }
        Logger.printlnAndLog("${cards.size} cards have been loaded.")
    }

    private fun exportCardsImpl(exportFile : File) {
        for (card in flashcards) {
            val(term, definition) = card
            exportFile.appendText(
                "${term}:${definition}:${statistics[term]}\n"
            )
        }
        Logger.printlnAndLog("${flashcards.size} cards have been saved.")
    }

    private fun checkAnswer(
        flashcard: Pair<Term, Definition>,
        answer: Definition
    ) {
        val (term, definition) = flashcard
        if (answer == definition) {
            Logger.printlnAndLog("Correct!")
        } else {
            statistics[term] = statistics[term]!!.plus(1)
            Logger.printlnAndLog(
                "Wrong. The right answer is \"${
                    flashcard
                        .second
                }\"" + if (flashcards.containsValue(answer)) {
                    " but your definition is correct for \"${getTerm(answer)}\""
                } else {
                    "."
                }
            )
        }
    }

    private fun getTerm(definition: Definition): Term {
        return flashcards.filterValues { it == definition }.keys.first()
    }
}