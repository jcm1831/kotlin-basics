package calculator

fun isAssignment(input: String) = input.contains("=")

fun isNoAssignment(input: String) = !isAssignment(input)

fun isVariable(input: String): Boolean {
    return "[a-zA-Z]+".toRegex().matches(input)
}

fun isNumber(input: String): Boolean {
    return "[+-]?\\d+".toRegex().matches(input)
}

fun isValidLhs(lhs: String): Boolean {
    return if (isVariable(lhs)) {
        true
    } else {
        println("Invalid identifier")
        false
    }
}

fun isValidRhs(rhs: String): Boolean {
    return if (isVariable(rhs) || isNumber(rhs) ||
        isValidExpression(rhs)) {
        true
    } else {
        println("Invalid assignment")
        false
    }
}

fun splitIntoLhsAndRhs(input: String): Pair<String, String> {
    val aux = input.split("=", " =", "= ", " = ", limit = 2)
    return Pair(aux.first().trim(), aux.last().trim())
}

fun isValidAssignment(input: String): Boolean {
    val (lhs, rhs) = splitIntoLhsAndRhs(input)
    return isValidLhs(lhs) && isValidRhs(rhs)
}

fun assignVariableToCache(assignment: String) {
    if (isValidAssignment(assignment)) {
        val (lhs, rhs) = splitIntoLhsAndRhs(assignment)
        if (isNumber(rhs)) {
            addVariable(Pair(lhs, rhs.toBigInteger()))
        } else if (isVariable(rhs)) {
            val value = getValue(rhs)
            if (value != null) addVariable(Pair(lhs, value))
        } else if (isExpression(rhs)) {
            val result = calculate(rhs)
            if (result != null) addVariable(Pair(lhs, result))
        }
    }
}


