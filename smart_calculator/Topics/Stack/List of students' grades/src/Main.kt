import java.util.*

fun main() {
    val list = readln().split(" ").map{ it.toDouble() }
    // write your code here
    val stack = Stack<Double>()
    stack.addAll(list)
    while (stack.isNotEmpty()) {
        print(stack.pop())
        print(" ")
    }
}