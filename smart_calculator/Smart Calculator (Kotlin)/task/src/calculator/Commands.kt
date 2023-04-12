package calculator

fun isCommand(input: String) = input.startsWith("/")
fun isNoCommand(input: String) = !isCommand(input)
fun isNotExit(input: String) = input != "/exit"
fun printHelp() {
    println(
        "The program performs all elementary arithmetic operations and " +
                "exponentiation on an arbitrary number of input operands. " +
                "The operator precedence follows the common definition: " +
                "(1) parentheses, (2) exponentiation, (3) " +
                "multiplication/division and (4) addition/subtraction."
    )
}

fun exit() {
    println("Bye!")
}

val commands = mapOf(
    "/help" to ::printHelp,
    "/exit" to ::exit,
)

fun executeCommand(input: String) {
    val command = commands[input]
    if (command != null) {
        command()
    } else {
        println("Unknown command")
    }
}

