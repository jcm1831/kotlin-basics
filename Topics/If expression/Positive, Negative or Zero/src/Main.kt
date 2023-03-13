fun main() {
    // write your code here
    val input = readln().toInt()
    println(
        if (input < 0) "negative"
        else if (input > 0) "positive"
        else "zero"
    )
}