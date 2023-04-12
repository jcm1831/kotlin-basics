fun solution(numbers: List<Int>, number: Int): MutableList<Int> {
    // put your code here
    val mutableNumbers = numbers.toMutableList()
    mutableNumbers.add(number)
    return mutableNumbers
}