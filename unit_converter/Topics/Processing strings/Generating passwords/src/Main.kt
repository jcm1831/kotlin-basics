fun generateRandomLowerCaseString(length: Int): CharArray {
    val allowedChars = ('a'..'z')
    val output = (1..length).map { allowedChars.random() }.joinToString("")
        .toCharArray()
    removeSuccessiveDuplicates(output, allowedChars)
    return output
}

fun insertUpperCaseLetters(password: CharArray, numUpperCaseLetters: Int) {
    var counter = 0
    while (counter != numUpperCaseLetters) {
        val index = password.indices.random()
        if (!password[index].isUpperCase()) {
            password[index] = password[index].uppercaseChar()
            ++counter
        }
    }
}

fun insertDigits(password: CharArray, numDigits: Int) {
    val allowedDigits = ('0'..'9')
    var counter = 0
    while (counter != numDigits) {
        val index = password.indices.random()
        if (!password[index].isDigit() && !password[index].isUpperCase()) {
            password[index] = allowedDigits.random()
            ++counter
        }
    }
    removeSuccessiveDuplicates(password, allowedDigits)
}

fun removeSuccessiveDuplicates(input: CharArray, validValues: CharRange) {
    for (index in 1 until input.size) {
        if (input[index - 1] == input[index]) {
            do {
                input[index] = validValues.random()
            } while (input[index - 1] == input[index])
        }
    }
}


fun main() {
    // get password requirements from input stream
    val requirements = readln().split(" ").map { it.toInt() }.toMutableList()
    val numUpperCaseLetters = requirements[0]
    val numDigits = requirements[2]
    val numSymbols = requirements[3]
    // generate random lower case string with N symbols
    val password = generateRandomLowerCaseString(numSymbols)
    // println(password)
    // generate upper case letters
    if (numUpperCaseLetters > 0) {
        insertUpperCaseLetters(password, numUpperCaseLetters)
    }
    // println(password)
    // generate digits
    if (numDigits > 0) {
        insertDigits(password, numDigits)
    }
    // print password
    println(password)
}