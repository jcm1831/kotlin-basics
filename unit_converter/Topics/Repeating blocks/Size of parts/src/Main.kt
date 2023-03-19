fun main() {
    // put your code here
    val numParts = readln().toInt()
    var numPerfect = 0
    var numLarger = 0
    var numRejections = 0
    repeat(numParts) {
        when (val detectorOutput = readln().toInt()) {
            -1 -> ++numRejections
            0 -> ++numPerfect
            1 -> ++numLarger
            else -> println("Invalid detector output $detectorOutput!")
        }
    }
    println("$numPerfect $numLarger $numRejections")
}