import java.math.BigInteger

fun max(a:BigInteger, b:BigInteger) = (a+b+(a-b).abs()) / BigInteger.valueOf(2)

fun main() {
    // write your code here
    val a = readln().toBigInteger()
    val b = readln().toBigInteger()
    println(max(a,b))
}