package encryptdecrypt

import java.io.File

val lowerCaseLetters = 'a'.code..'z'.code
val upperCaseLetters = 'A'.code..'Z'.code

fun ringShift(code: Int, codeRange: IntRange, key: Int): Char {
    val effectiveKey = key % (codeRange.last - codeRange.first)
    val delta = if (effectiveKey >= 0) {
        (codeRange.last - code) - effectiveKey
    } else {
        (code - codeRange.first) + effectiveKey
    }
    return when {
        delta >= 0 -> (code + effectiveKey).toChar()
        else -> {
            if (effectiveKey >= 0) {
                (codeRange.first - delta - 1).toChar()
            } else {
                (codeRange.last + delta + 1).toChar()
            }
        }
    }
}

fun shiftEncrypt(message: String, key: Int): String {
    var encryptedMessage = ""
    message.forEach { symbol ->
        if (symbol.isLetter()) {
            encryptedMessage += when {
                symbol.isLowerCase() ->
                    ringShift(symbol.code, lowerCaseLetters, key)

                symbol.isUpperCase() ->
                    ringShift(symbol.code, upperCaseLetters, key)

                else -> ""
            }
        } else {
            encryptedMessage += symbol
        }
    }
    return encryptedMessage
}

fun unicodeEncrypt(message: String, key: Int): String {
    var encryptedMessage = ""
    message.forEach { symbol -> encryptedMessage += (symbol + key) }
    return encryptedMessage
}

fun transformMessage(inputArguments: InputArguments, message: String): String {
    return when {
        inputArguments.isShiftEncrypt() ->
            shiftEncrypt(message, inputArguments.key)

        inputArguments.isUnicodeEncrypt() ->
            unicodeEncrypt(message, inputArguments.key)

        else -> ""
    }
}

fun main(args: Array<String>) {
    // parse input arguments
    val inputArguments = InputArguments().parseArguments(args)

    // generate encrypted/decrypted message
    val message = when {
        inputArguments.hasData() -> {
            transformMessage(inputArguments, inputArguments.data)
        }

        inputArguments.hasInputFileName() -> {
            val data = inputArguments.readInputFile()
            transformMessage(inputArguments, data)
        }

        else -> {
            inputArguments.errorMessage = "No data available!"
            ""
        }
    }

    // check for errors and print result
    if (inputArguments.areValid()) {
        when {
            inputArguments.hasOutputFileName() -> {
                // print to file
                File(inputArguments.outputFileName).writeText(message)
            }
            // print to standard output
            else -> println(message)
        }
    } else {
        inputArguments.printErrorMessage()
    }

}