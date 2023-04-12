fun main() {
    // write your code here
    val input = readln()
    var frequency = 1
    var output = if (input.isNotEmpty()) input.first().toString() else ""
    for (i in input.indices) {
        if (i != input.indices.last()) {
            if (input[i] == input[i + 1]) {
                ++frequency
            } else {
                output += frequency.toString() + input[i + 1]
                frequency = 1
            }
        } else {
            output += frequency
        }
    }
    println(output)
}