fun main() {
    // write your code here
    val numbers = MutableList(readln().toInt()) { readln().toInt() }
    var minimum = numbers.first()
    for (i in numbers) {
        if (i < minimum) {
            minimum = i
        }
    }
    print(minimum)
}