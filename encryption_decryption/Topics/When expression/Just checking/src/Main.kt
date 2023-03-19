fun main() {
    // write your code here
    println(when (readln().toInt()) {
        2 -> "Yes!"
        1,3,4 -> "No!"
        else -> "Unknown number"
    })
}