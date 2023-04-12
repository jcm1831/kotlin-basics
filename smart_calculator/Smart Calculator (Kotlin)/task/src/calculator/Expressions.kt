package calculator

import java.math.BigInteger

fun formatExpression(expression: String): String {
    // simplify multiple + and even number of -
    val output = expression.replace("((\\+)|(--))+".toRegex(), "+")
    // simplify odd number of - and multiple +-/-+
    return output.replace("((\\+?-\\+)|(\\+-\\+?)|(-))+".toRegex(), "-")
}

fun isExpression(input: String): Boolean {
    return isNoCommand(input) && isNoAssignment(input) && isNotEmpty(input)
}

fun isValidExpression(expression: String): Boolean {
    // check for sequences of *, / or ^
    if ("[*/^]{2,}".toRegex().containsMatchIn(expression)) {
        return false
    }
    // check for unbalanced number of brackets
    if (expression.count { it == '(' } != expression.count { it == ')' }) {
        return false
    }
    // check for invalid variables
    if ("(\\d+[a-zA-Z]+)|([a-zA-Z]+\\d+)".toRegex()
            .containsMatchIn(expression)
    ) {
        return false
    }
    return "\\(*[+-]?(\\d|[a-zA-Z])+(\\)*[+-/*^]\\(*[+-]?(\\d|[a-zA-Z])+)*\\)*".toRegex()
        .matches(expression)
}

fun decompose(expression: String): MutableList<String> {
    return "[+-/*^()]|(\\d|[a-zA-Z])+".toRegex().findAll(expression)
        .map { it.value }.toMutableList()
}

fun isOperator(input: String) = "[+-/*^()]".toRegex().matches(input)

fun isHigherPrecedence(incoming: String, top: String): Boolean {
    return when {
        incoming == top -> false
        incoming == "^" -> true
        ((incoming == "*" || incoming == "/") && top != "^") -> true
        else -> false
    }
}

fun isLowerOrEqualPrecedence(incoming: String, top: String) =
    !isHigherPrecedence(incoming, top)

fun convertToPostfix(expression: String): MutableList<String> {
    // decompose expression into mutable list of strings
    val input = decompose(expression)
    // prepare output list and stack
    val output = mutableListOf<String>()
    val stack = ArrayDeque<String>()
    // do postfix conversion
    while (input.isNotEmpty()) {
        if (isOperator(input.first())) {
            if (stack.isEmpty() || stack.first() == "(") {
                stack.addFirst(input.removeFirst())
            } else if (input.first() == "(") {
                stack.addFirst(input.removeFirst())
            } else if (input.first() == ")") {
                // add operators from stack until "("
                while (stack.first() != "(") output.add(stack.removeFirst())
                // remove "(" from stack
                stack.removeFirst()
                // remove ")" from input
                input.removeFirst()
            } else if (isHigherPrecedence(input.first(), stack.first())) {
                stack.addFirst(input.removeFirst())
            } else if (isLowerOrEqualPrecedence(input.first(), stack.first())) {
                while (stack.isNotEmpty() && stack.first() != "(" &&
                    isLowerOrEqualPrecedence(input.first(), stack.first())
                ) {
                    output.add(stack.removeFirst())
                }
                stack.addFirst(input.removeFirst())
            }
        } else {
            output.add(input.removeFirst())
        }
    }
    // add remaining operators on stack to result
    output.addAll(stack)
    // return expression in postfix notation
    return output
}

fun calculate(expression: String): BigInteger? {
    val postfix = convertToPostfix(expression)
    // check for unary -/+
    if (postfix.size == 2) {
        return postfix.first().toBigInteger() *
                if (postfix.last() == "-") {
                    BigInteger.valueOf(-1)
                } else {
                    BigInteger.valueOf(1)
                }
    } else {
        val stack = ArrayDeque<BigInteger>()
        while (postfix.isNotEmpty()) {
            if (isNumber(postfix.first())) {
                stack.addFirst(postfix.removeFirst().toBigInteger())
            } else if (isVariable(postfix.first())) {
                val value = getValue(postfix.removeFirst())
                if (value != null) {
                    stack.addFirst(value)
                } else {
                    return null
                }
            } else if (isOperator(postfix.first())) {
                val op = getOperation(postfix.removeFirst())
                if (op != null) {
                    val op2 = stack.removeFirst()
                    val op1 = stack.removeFirst()
                    stack.addFirst(op(op1, op2))
                } else {
                    return null
                }
            }
        }
        return stack.first()
    }
}

fun evaluateExpression(input: String) {
    val expression = formatExpression(input)
    if (isValidExpression(expression)) {
        val result = calculate(expression)
        if (result != null) println(result)
    } else {
        println("Invalid expression")
    }
}



