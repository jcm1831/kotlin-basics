fun main() {
    // write your code here
    val firstRange = readln().toInt() .. readln().toInt()
    val secondRange = readln().toInt() .. readln().toInt()
    val num = readln().toInt()
    println(num in firstRange || num in secondRange)
}