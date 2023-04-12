fun stringToMap(first: String, second: String, third: String): Map<String, Int> {
    // put your code here
    val makeEntry = { s: String -> s to s.length }
    return mapOf(makeEntry(first), makeEntry(second), makeEntry(third))
}