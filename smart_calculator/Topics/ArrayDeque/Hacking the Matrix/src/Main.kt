fun main() {
    val list = readln().split(" ").map { it.toInt() }.toMutableList()
    // write your code here
    for (i in list.indices) {
        if (i % 2 == 0) {
            print("${list.first()} ")
            list.removeFirst()
        } else {
            print("${list.last()} ")
            list.removeLast()
        }
    }
}