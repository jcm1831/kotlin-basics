fun main() {
    // write your code here
    val validAge = readln().toUInt()
    val lowerBound = 18u
    val upperBound = 59u
    println(validAge in lowerBound..upperBound)
}