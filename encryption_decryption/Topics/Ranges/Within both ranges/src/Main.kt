fun main() {
    // write your code here
    val firstRange = readln().toInt() .. readln().toInt()
    val secondRange = readln().toInt() .. readln().toInt()
    val numInput = readln().toInt()
    println(numInput in firstRange && numInput in secondRange)
}