fun main() {
    val text = readln()
    // write your code here
    val processedText = text.replace("[aA]+".toRegex(), "a")
    println(processedText)
}