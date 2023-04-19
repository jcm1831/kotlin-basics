package wordsvirtuoso

import kotlin.system.exitProcess

class Game(wordsFilepath: String, candidatesFilepath: String) {
    private val words = WordsSet()
    private val candidateWords = WordsSet()
    private val clueWords = WordsList()
    private val inputWord = InputWord()
    private val secretWord : Word
    private val gameMetrics = GameMetrics()

    init {
        // parse words from input files
        words.readFromFile(wordsFilepath, "words")
        candidateWords.readFromFile(candidatesFilepath, "candidate words")
        // check whether all candidate words are contained in words file
        if (words.containsNotAll(candidateWords)) {
            val sum = (words + candidateWords)
            val diffSet = sum - words
            println(
                "Error: ${diffSet.number()} candidate words are not " +
                        "included in the $wordsFilepath file."
            )
            exitProcess(-1)
        }
        // select secret word
        secretWord = candidateWords.random()
    }

    fun run() {
        println("Words Virtuoso")
        // enter game loop
        gameMetrics.setStart()
        while (getNewInputWord()) {
            if (isValidInput(inputWord.toWord())) {
                clueWords.add(inputWord.getClueWord(secretWord))
                clueWords.print()
                if (inputWord.isEqualTo(secretWord)) {
                    gameMetrics.setEnd()
                    println("Correct!")
                    if (gameMetrics.numberOfTurnsIsOne()) {
                        println("Amazing luck! The solution was found at once.")
                    } else {
                        gameMetrics.printMetrics()
                    }
                    break
                } else {
                    inputWord.printWrongCharacters()
                }
            } else {
                continue
            }
        }
    }

    private fun getNewInputWord() : Boolean {
        println()
        println("Input a 5-letter word:")
        inputWord.getFromTerminal()
        return if (inputWord.isExit()) {
            println("The game is over.")
            false
        } else {
            gameMetrics.incrementNumberOfTurns()   
            true
        }
    }
    private fun isValidInput(inputWord : Word) : Boolean {
        return !(inputWord.isInvalidWord(print = true) || words.containsNot(inputWord))
    }
}