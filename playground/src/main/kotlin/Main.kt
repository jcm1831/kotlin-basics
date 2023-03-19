import java.io.File

fun getNumbersCount(): Int {
    val separator = File.separator
    val fileName = "src${separator}words_with_numbers.txt"
    var numbersCount = 0
    File(fileName).forEachLine { line ->
        if (line.toCharArray().all { char -> return@all char.isDigit() }) {
            ++numbersCount
        }
    }
    return numbersCount
}

fun findLongestWord(): String {
    val separator = File.separator
    val fileName = "src${separator}words_sequence.txt"
    var longestWord = ""
    File(fileName).forEachLine { line ->
        if (line.length > longestWord.length) {
            longestWord = line
        }
    }
    return longestWord
}

fun main(args: Array<String>) {
    println(getNumbersCount())
    println(findLongestWord().length)
}