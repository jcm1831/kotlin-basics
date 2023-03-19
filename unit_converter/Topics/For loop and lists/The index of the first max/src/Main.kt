fun main() {
    // write your code here
    val listSize = readln().toInt()
    val list = MutableList(listSize) { readln().toInt() }

    var maxIndex = 0
    var maxValue = list.first()
    for (index in list.indices) {
        if (list[index] > maxValue) {
            maxIndex = index
            maxValue = list[index]
        }
    }
    print(maxIndex)
}