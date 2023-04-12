package calculator

import java.math.BigInteger

val variableCache = mutableMapOf<String, BigInteger>()

fun addVariable(variable: Pair<String, BigInteger>) {
    variableCache += variable
}

fun getValue(name: String): BigInteger? {
    return if (variableCache.containsKey(name)) {
        variableCache[name]
    } else {
        println("Unknown variable")
        null
    }
}
