import java.util.Scanner
fun main() {
    // write your code here
    val scanner = Scanner(System.`in`)
    val s = scanner.next()
    val n = scanner.nextInt()
    if (n <= s.length) {
        println(s.substring(n, s.length) + s.substring(0, n))
    } else {
        println(s)
    }

}