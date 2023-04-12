fun main() {
    // write your code here
    val input = readln()
    val output = "\\(?[0-8]{3}\\)?-?[0-8]{3}-?[0-8]{4}".toRegex().findAll(input)
    for (match in output) println(match.value)
}