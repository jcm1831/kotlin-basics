package calculator

fun formatInput(input: String) : String {
    // remove redundant whitespace
    return input.replace(" +".toRegex(), "")
}

fun isNotEmpty(input: String) = input.isNotEmpty()

fun main() {
    do {
        val input = formatInput(readln())
        if (isCommand(input)) {
            executeCommand(input)
        } else if (isExpression(input)) {
            evaluateExpression(input)
        } else if (isAssignment(input)) {
            assignVariableToCache(input)
        } else {
            continue
        }
    } while (isNotExit(input))
}


