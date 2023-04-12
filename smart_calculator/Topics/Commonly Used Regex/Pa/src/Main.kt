fun main() {
    val text = readln()
    // write your code here
    val output = "([a-zA-Z])*(pa)+([a-zA-Z])*".toRegex().findAll(text)
    for (match in output) println(match.value)
}