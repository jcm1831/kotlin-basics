fun main() {
    // write your code here
    val listSize = readln().toInt()
    val list = mutableListOf<Int>()
    for (i in 0 until listSize) {
        list.add(i, readln().toInt())
    }
    val numbers = readln().split(" ").map { it.toInt() }.toMutableList()
    val p = numbers.first()
    val m = numbers.last()

    var areAdjacent : Boolean = false
    for (index in 0 until list.lastIndex) {
        if (list[index] == p && list[index+1] == m ||
            list[index] == m && list[index+1] == p) {
            areAdjacent = true
            break
        }
    }
    println(if(areAdjacent) "NO" else "YES");
}