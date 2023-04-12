import java.math.BigInteger

fun percent(a: BigInteger, b: BigInteger) : BigInteger {
    val factor = BigInteger.valueOf(100)
    return (factor * a) / (a + b)
}

fun main() {
    // write your code here
    val a = readln().toBigInteger()
    val b = readln().toBigInteger()
    println("${percent(a, b)}% ${percent(b, a)}%")
}