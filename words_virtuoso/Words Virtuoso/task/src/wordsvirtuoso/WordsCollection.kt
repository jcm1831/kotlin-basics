package wordsvirtuoso

import java.io.File
import kotlin.system.exitProcess

class WordsSet(private val words: MutableSet<Word> = mutableSetOf()) {
    operator fun plus(other: WordsSet): WordsSet {
        return WordsSet((words + other.words).toMutableSet())
    }
    operator fun minus(other: WordsSet): WordsSet {
        return WordsSet((words - other.words).toMutableSet())
    }
    fun readFromFile(filepath: String = "", fileType: String = "") {
        if (File(filepath).exists()) {
            words.addAll(File(filepath).readLines().map { Word(it.lowercase()) })
            val invalidWords = words.count { it.isInvalidWord() }
            if (invalidWords > 0) {
                println(
                    "Error: $invalidWords invalid words were found in the " +
                            "$filepath file."
                )
                exitProcess(-1)
            }
        } else {
            println("Error: The $fileType file $filepath doesn't exist.")
            exitProcess(-1)
        }
    }
    fun containsNot(word: Word) : Boolean {
        return if (!words.contains(word)) {
            println("The input word isn't included in my words list.")
            true
        } else {
            false
        }
    }
    fun containsNotAll(other: WordsSet) = !words.containsAll(other.words)
    fun random() = words.random()
    fun number() = words.size
}

class WordsList(private val words: MutableList<Word> = mutableListOf()) {
    fun add(word : Word) = words.add(word)
    fun print() {
        println()
        words.forEach { it.print() }
        println()
    }
}