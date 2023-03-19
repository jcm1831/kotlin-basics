fun main() {
    val totalSeconds = System.currentTimeMillis() / 1000 // do not change this line
    // enter your code
    val timeSeconds = totalSeconds % 60
    val timeMinutes = (totalSeconds / 60) % 60
    val timeHours = (totalSeconds / 3600) % 24
    println("$timeHours:$timeMinutes:$timeSeconds")
}