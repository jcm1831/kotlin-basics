package wordsvirtuoso

data class GameMetrics(private var numberOfTurns : Int = 0) {
    private var start = 0L
    private var end = 0L

    fun numberOfTurnsIsOne() = numberOfTurns == 1
    fun incrementNumberOfTurns() = ++numberOfTurns
    fun setStart() {
        start = System.currentTimeMillis()
    }
    fun setEnd() {
        end = System.currentTimeMillis()
    }
    fun printMetrics() {
        println("The solution was found after $numberOfTurns tries in " +
                "${duration()} seconds.")
    }
    private fun duration() = end - start
}