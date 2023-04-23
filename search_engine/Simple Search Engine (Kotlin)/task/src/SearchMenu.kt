package search

import java.io.File
import kotlin.system.exitProcess

typealias optionCallback = () -> Unit

class SearchMenu(filepath: String) {
    private val data = arrayListOf<String>()
    private val invertedIndex = mutableMapOf<String,MutableSet<Int>>()
    private val callbacks = mapOf(
        0 to { exit() },
        1 to { search() },
        2 to { print() }
    )

    init {
        val file = File(filepath)
        if (file.exists()) {
            data.addAll((file.readLines()))
            generateInvertedIndex()
        } else {
            println("Unknown input file!")
            exitProcess(-1)
        }
    }

    fun runSearchEngine() {
        while (true) {
            println("=== Menu ===")
            println("1. Find a person")
            println("2. Print all people")
            println("0. Exit")
            val option = readln().toInt()
            if (option == 0) {
                getOption(0)!!.invoke()
                break
            } else {
                val callback = getOption(option)
                if (callback != null) {
                    callback.invoke()
                } else {
                    println("Incorrect option! Try again")
                }
                println()
            }
        }
    }

    private fun generateInvertedIndex() {
        data.forEachIndexed { index, s ->
            val words = s.split(" ").map { it.lowercase() }
            words.forEach {word ->
                if (!invertedIndex.containsKey(word)) {
                    invertedIndex[word] = mutableSetOf(index)
                } else {
                    invertedIndex[word]!!.add(index)
                }
            }
        }
    }

    private fun getOption(option: Int): optionCallback? {
        return if (callbacks.containsKey(option)) {
            return callbacks[option]
        } else {
            null
        }
    }

    private fun search() {
        println("Select a matching strategy: ALL, ANY, NONE")
        val strategy = MatchingStrategy.Factory.create(Strategies.valueOf(readln().uppercase()))
        println()
        if (strategy != null) {
            println("Enter a name or email to search all matching people.")
            val searchResults = strategy.match(data, invertedIndex)
            if (searchResults.isNotEmpty()) {
                searchResults.forEach { println(it) }
            } else {
                println("No matching people found.")
            }
        }
    }

    private fun print() {
        println("=== List of people ===")
        data.forEach { println(it) }
    }

    private fun exit() = println("Bye!")
}