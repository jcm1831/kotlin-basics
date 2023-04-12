fun main() {
    // write your code here
    val inputString = readln().split("-")
    val outputString =
        inputString[1] + "/" + inputString.last() + "/" + inputString.first()
    println(outputString)
}