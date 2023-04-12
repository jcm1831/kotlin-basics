fun summator(map: Map<Int, Int>): Int {
    // put your code here
    var sum = 0
    for ((k,v) in map) {
        sum += if (k % 2 == 0) v else 0
    }
    return sum
}