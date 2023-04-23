enum class Strategies {
    ALL,
    ANY,
    NONE,
    INVALID
}

interface MatchingStrategy {
    fun match(
        data: ArrayList<String>,
        invertedIndex: MutableMap<String, MutableSet<Int>>
    ): Set<String>

    object Factory {
        fun create(strategy: Strategies): MatchingStrategy? {
            return when (strategy) {
                Strategies.ALL -> MatchAll()
                Strategies.ANY -> MatchAny()
                Strategies.NONE -> MatchNone()
                Strategies.INVALID -> {
                    println("Invalid matching strategy!")
                    null
                }
            }
        }
    }
}


class MatchAll : MatchingStrategy {
    override fun match(
        data: ArrayList<String>,
        invertedIndex: MutableMap<String, MutableSet<Int>>
    ): Set<String> {
        val matchers = readln().lowercase().split(" ")
        val searchResults = mutableSetOf<String>()
        return if (invertedIndex.keys.containsAll(matchers)) {
            var indexSet = invertedIndex[matchers.first()]!!
            matchers.forEach { matcher ->
                indexSet =
                    (indexSet intersect invertedIndex[matcher]!!).toMutableSet()
            }
            searchResults
        } else {
            searchResults
        }
    }
}

class MatchAny : MatchingStrategy {
    override fun match(
        data: ArrayList<String>,
        invertedIndex: MutableMap<String, MutableSet<Int>>
    ): Set<String> {
        val matchers = readln().lowercase().split(" ")
        val searchResults = mutableSetOf<String>()
        matchers.forEach { matcher ->
            if (invertedIndex.containsKey(matcher)) {
                invertedIndex[matcher]!!.forEach { searchResults.add(data[it]) }
            }
        }
        return searchResults
    }
}

class MatchNone : MatchingStrategy {
    override fun match(
        data: ArrayList<String>,
        invertedIndex: MutableMap<String, MutableSet<Int>>
    ): Set<String> {
        val matchers = readln().lowercase().split(" ")
        val searchResults = mutableSetOf<String>()
        searchResults.addAll(data)
        matchers.forEach { matcher ->
            if (invertedIndex.containsKey(matcher)) {
                invertedIndex[matcher]!!.forEach { searchResults.remove(data[it]) }
            }
        }
        return searchResults
    }
}