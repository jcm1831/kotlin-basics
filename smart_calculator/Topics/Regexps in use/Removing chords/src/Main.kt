fun main() {
    val text = readln()
    val processedText = text.replace("(Am|A|Em|E|Dm|D|G|C)\\s".toRegex(), "")
    println(processedText)
}