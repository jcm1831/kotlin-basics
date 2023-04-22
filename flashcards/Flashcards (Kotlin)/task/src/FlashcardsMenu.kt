package flashcards

typealias action = () -> Unit

class FlashcardsMenu(private val flashcards: FlashcardCollection) {
    private val actionCallbacks = mapOf(
        "add" to { flashcards.addCard() },
        "remove" to { flashcards.removeCard() },
        "import" to { flashcards.importCards() },
        "export" to { flashcards.exportCards() },
        "ask" to { flashcards.askForDefinitions() },
        "log" to { Logger.writeLogFile() },
        "hardest card" to { flashcards.getHardestCard() },
        "reset stats" to { flashcards.resetStatistics() },
        "exit" to { exit() }
    )

    fun inputAction() {
        while (true) {
            Logger.printlnAndLog(
                "Input the action (add, remove, import, export, ask, " +
                        "exit, log, hardest card, reset stats):"
            )
            val action = Logger.readlnAndLog()
            val callback = getAction(action)
            if (callback != null) {
                callback()
                if (action == "exit") {
                    val exportFile = FileHandler.createExportFile()
                    if (exportFile!= null) flashcards.exportCards(exportFile)
                    break
                } else {
                    Logger.printlnAndLog()
                    continue
                }
            }
        }
    }

    private fun getAction(action: String): action? {
        return if (actionCallbacks.contains(action)) {
            actionCallbacks[action]
        } else {
            Logger.printlnAndLog("Unknown main menu action!")
            null
        }
    }

    private fun exit() = Logger.printlnAndLog("Bye bye!")
}