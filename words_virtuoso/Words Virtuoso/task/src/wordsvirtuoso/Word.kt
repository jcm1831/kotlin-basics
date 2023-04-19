package wordsvirtuoso


data class Word(var string: String = "") {
    operator fun get(index: Int) = string[index]
    fun isInvalidWord(print : Boolean = false): Boolean {
        if (print) {
            when {
                isNotFiveLetter() -> {
                    println("The input isn't a 5-letter word.")
                }
                containsInvalidCharacters() -> {
                    println("One or more letters of the input aren't valid.")
                }
                containsDuplicates() -> {
                    println("The input has duplicate letters.")
                }
            }
        }
        return (isNotFiveLetter() || containsInvalidCharacters() || containsDuplicates())
    }
    fun print() = println(string)
    fun contains(char: Char) = string.contains(char)

    private fun isNotFiveLetter() = string.length != 5
    private fun containsInvalidCharacters() = "[^a-zA-Z]".toRegex().containsMatchIn(string)
    private fun containsDuplicates() = "(.)\\1+".toRegex().containsMatchIn(string)
}

class InputWord {
    private var word = Word()
    private var wrongCharacters = arrayOf<Char>()

    fun toWord() = word

    fun getClueWord(other: Word): Word {
        var clueString = ""
        word.string.forEachIndexed { index, c ->
            clueString += if (c == other[index]) {
                // same position: green
                "\u001B[48:5:10m${c.uppercaseChar()}\u001B[0m"
            } else if (other.contains(c)) {
                // other position: yellow
                "\u001B[48:5:11m${c.uppercaseChar()}\u001B[0m"
            } else {
                if (!wrongCharacters.contains(c.uppercaseChar())) {
                    wrongCharacters += c.uppercaseChar()
                }
                // other case: grey
                "\u001B[48:5:7m${c.uppercaseChar()}\u001B[0m"
            }
        }
        wrongCharacters.sort()
        return Word(clueString)
    }

    fun printWrongCharacters() {
        println("\u001B[48:5:14m${wrongCharacters.joinToString("")}\u001B[0m")
    }
    fun getFromTerminal() {
        word.string = readln().lowercase()
    }
    fun isExit() = word.string == "exit"
    fun isEqualTo(other: Word) = word == other
}