fun main() {
    val studentsMarks = mutableMapOf<String, Int>()
    // read key-value pairs until "stop" is entered
    while (true) {
        val key = readln()
        if (key == "stop") break
        val value = readln().toInt()
        if (!studentsMarks.contains(key)) studentsMarks += key to value
    }
    println(studentsMarks)
}