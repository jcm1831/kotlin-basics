package encryptdecrypt

import java.io.File

class InputArguments(
    var alg: String = "shift",
    var mode: String = "dec",
    var data: String = "",
    var key: Int = 5,
    var inputFileName: String = "input.txt",
    var outputFileName: String = "output.txt",
    var errorMessage: String = ""
) {
    fun parseArguments(args: Array<String>): InputArguments {
        for (index in args.indices) {
            when (args[index]) {
                "-mode" -> {
                    mode = args[index + 1]
                    if (!isEncode().xor(isDecode())) {
                        errorMessage += "Invalid operation mode!"
                    }
                }

                "-key" -> key = args[index + 1].toInt()
                "-data" -> data = args[index + 1]
                "-in" -> inputFileName = args[index + 1]
                "-out" -> outputFileName = args[index + 1]
                "-alg" -> {
                    alg = args[index + 1]
                    if (!isShiftEncrypt().xor(isUnicodeEncrypt())) {
                        errorMessage += "Unknown encoding/decoding algorithm!"
                    }
                }
            }
        }
        // update sign of key for encoding/decoding
        if (isDecode()) key *= -1
        return this
    }

    fun hasInputFileName() = inputFileName.isNotEmpty()
    fun hasOutputFileName() = outputFileName.isNotEmpty()
    fun hasData() = data.isNotEmpty()
    fun isEncode() = mode == "enc"
    fun isDecode() = mode == "dec"
    fun isShiftEncrypt() = alg == "shift"
    fun isUnicodeEncrypt() = alg == "unicode"
    fun areValid() = errorMessage.isEmpty()

    fun readInputFile(): String {
        return if (File(inputFileName).exists()) {
            File(inputFileName).readText()
        } else {
            errorMessage += "Invalid input file!"
            ""
        }
    }

    fun printErrorMessage() = println("Error: $errorMessage")
}