fun main() {
    // write your code here
    val a = readln().toUInt()
    val b = readln().toUInt()
    val c = readln().toUInt()
    println(
        if (a >= b && a >= c && a < (b + c)) {
            "YES"
        } else if (b >= a && b >= c && b < (a + c)) {
            "YES"
        } else if (c >= a && c >= b && c < (a + b)) {
            "YES"
        } else {
            "NO"
        }
    )
}