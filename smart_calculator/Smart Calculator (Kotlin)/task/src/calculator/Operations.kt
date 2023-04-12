package calculator

import java.math.BigInteger

fun add(a1: BigInteger, a2: BigInteger) = a1 + a2
fun subtract(m: BigInteger, s: BigInteger) = m - s
fun multiply(f1: BigInteger, f2: BigInteger) = f1 * f2
fun divide(num: BigInteger, denom: BigInteger) = num / denom
fun power(base: BigInteger, exponent: BigInteger) : BigInteger {
    return if (exponent == BigInteger.valueOf(1)) {
        base
    } else {
        base * power(base, exponent - BigInteger.valueOf(1))
    }
}

typealias Operation = (BigInteger, BigInteger) -> BigInteger

val operations = mapOf(
    "+" to ::add,
    "-" to ::subtract,
    "*" to ::multiply,
    "/" to ::divide,
    "^" to ::power
)

fun getOperation(op: String): Operation? {
    val operation = operations[op]
    return if (operation != null) {
        operation
    } else {
        println("Unknown operation")
        null
    }
}