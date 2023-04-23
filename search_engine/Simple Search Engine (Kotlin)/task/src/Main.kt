package search

import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.size == 2 && args.first() == "--data") {
        val menu = SearchMenu(args.last())
        menu.runSearchEngine()
    } else {
        println("Invalid command line arguments")
        exitProcess(-1)
    }
}
