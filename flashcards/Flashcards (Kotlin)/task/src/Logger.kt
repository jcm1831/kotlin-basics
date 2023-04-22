package flashcards

object Logger {
    private val logEntries = mutableListOf<String>()
    fun printlnAndLog(message: String = "") {
        println(message)
        logEntries.add(message)
    }
    fun readlnAndLog(): String {
        val input = readln()
        logEntries.add(input)
        return input
    }
    fun writeLogFile() {
        val logFile = FileHandler.createExportFileFromInput()
        for (logEntry in logEntries) logFile.appendText(logEntry + "\n")
        printlnAndLog("The log has been saved.")
    }
}