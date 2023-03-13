enum class CurrencyDictionary(val country: String, val currency: String) {
    GERMANY("Germany", "Euro"),
    MALI("Mali", "CFA franc"),
    DOMINICA("Dominica", "Eastern Caribbean dollar"),
    CANADA("Canada", "Canadian dollar"),
    SPAIN("Spain", "Euro"),
    AUSTRALIA("Australia", "Australian dollar"),
    BRAZIL("Brazil", "Brazilian real"),
    SENEGAL("Senegal", "CFA franc"),
    FRANCE("France", "Euro"),
    GRENADA("Grenada", "Eastern Caribbean dollar"),
    KIRIBATI("Kiribati", "Australian dollar"),
    NULL("","")
}

fun findByCountry(country: String): CurrencyDictionary {
    for (dictionary in CurrencyDictionary.values()) {
        if (country == dictionary.country) return dictionary
    }
    return CurrencyDictionary.NULL
}


fun main() {
    // put your code here
    val countries = readln().split(" ").map { it }.toMutableList()
    val firstDictionary = findByCountry(countries.first())
    val secondDictionary = findByCountry(countries.last())
    print(firstDictionary.currency == secondDictionary.currency)
}