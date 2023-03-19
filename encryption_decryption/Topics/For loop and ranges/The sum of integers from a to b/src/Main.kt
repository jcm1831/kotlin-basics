fun main() {
    // put your code here
    val a = readln().toInt()
    val b = readln().toInt()

    var sum = 0
    for (i in a..b) {
        sum += i
    }
    println(sum)
}