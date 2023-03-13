package converter

import java.util.*

// free helper functions for conventional conversion
fun fromHubValue(value: Double, convRate: Double) = value * (1.0 / convRate)
fun toHubValue(value: Double, convRate: Double) = value * convRate

enum class Units(
    private val abbreviations: List<String>,
    private val singular: String,
    private val plural: String,
    private val typeTag: Char,
    private var value: Double = 0.0,
) {
    // properties
    METER(listOf("m"), "meter", "meters", 'd') {
        private val _convRate: Double = 1.0
        override fun toHubValue(value: Double) = toHubValue(value, _convRate)
        override fun fromHubValue(value: Double) =
            fromHubValue(value, _convRate)
    },
    KILOMETER(listOf("km"), "kilometer", "kilometers", 'd') {
        private val _convRate: Double = 1000.0
        override fun toHubValue(value: Double) = toHubValue(value, _convRate)
        override fun fromHubValue(value: Double) =
            fromHubValue(value, _convRate)
    },
    CENTIMETER(listOf("cm"), "centimeter", "centimeters", 'd') {
        private val _convRate: Double = 0.01
        override fun toHubValue(value: Double) = toHubValue(value, _convRate)
        override fun fromHubValue(value: Double) =
            fromHubValue(value, _convRate)
    },
    MILLIMETER(listOf("mm"), "millimeter", "millimeters", 'd') {
        private val _convRate: Double = 0.001
        override fun toHubValue(value: Double) = toHubValue(value, _convRate)
        override fun fromHubValue(value: Double) =
            fromHubValue(value, _convRate)
    },
    MILE(listOf("mi"), "mile", "miles", 'd') {
        private val _convRate: Double = 1609.35
        override fun toHubValue(value: Double) = toHubValue(value, _convRate)
        override fun fromHubValue(value: Double) =
            fromHubValue(value, _convRate)
    },
    YARD(listOf("yd"), "yard", "yards", 'd') {
        private val _convRate: Double = 0.9144
        override fun toHubValue(value: Double) = toHubValue(value, _convRate)
        override fun fromHubValue(value: Double) =
            fromHubValue(value, _convRate)
    },
    FOOT(listOf("ft"), "foot", "feet", 'd') {
        private val _convRate: Double = 0.3048
        override fun toHubValue(value: Double) = toHubValue(value, _convRate)
        override fun fromHubValue(value: Double) =
            fromHubValue(value, _convRate)
    },
    INCH(listOf("in"), "inch", "inches", 'd') {
        private val _convRate: Double = 0.0254
        override fun toHubValue(value: Double) = toHubValue(value, _convRate)
        override fun fromHubValue(value: Double) =
            fromHubValue(value, _convRate)
    },
    GRAM(listOf("g"), "gram", "grams", 'w') {
        private val _convRate: Double = 1.0
        override fun toHubValue(value: Double) = toHubValue(value, _convRate)
        override fun fromHubValue(value: Double) =
            fromHubValue(value, _convRate)
    },
    KILOGRAM(listOf("kg"), "kilogram", "kilograms", 'w') {
        private val _convRate: Double = 1000.0
        override fun toHubValue(value: Double) = toHubValue(value, _convRate)
        override fun fromHubValue(value: Double) =
            fromHubValue(value, _convRate)
    },
    MILLIGRAM(listOf("mg"), "milligram", "milligrams", 'w') {
        private val _convRate: Double = 0.001
        override fun toHubValue(value: Double) = toHubValue(value, _convRate)
        override fun fromHubValue(value: Double) =
            fromHubValue(value, _convRate)
    },
    POUND(listOf("lb"), "pound", "pounds", 'w') {
        private val _convRate: Double = 453.592
        override fun toHubValue(value: Double) = toHubValue(value, _convRate)
        override fun fromHubValue(value: Double) =
            fromHubValue(value, _convRate)
    },
    OUNCE(listOf("oz"), "ounce", "ounces", 'w') {
        private val _convRate: Double = 28.3495
        override fun toHubValue(value: Double) = toHubValue(value, _convRate)
        override fun fromHubValue(value: Double) =
            fromHubValue(value, _convRate)
    },
    CELSIUS(
        listOf("celsius", "dc", "c"),
        "degree Celsius",
        "degrees Celsius",
        't'
    ) {
        private val _convRate: Double = 1.0
        override fun toHubValue(value: Double) = toHubValue(value, _convRate)
        override fun fromHubValue(value: Double) =
            fromHubValue(value, _convRate)
    },
    FAHRENHEIT(
        listOf("fahrenheit", "df", "f"),
        "degree Fahrenheit",
        "degrees " + "Fahrenheit",
        't'
    ) {
        override fun toHubValue(value: Double) = (value - 32.0) * (5.0 / 9.0)
        override fun fromHubValue(value: Double) = value * (9.0 / 5.0) + 32.0
    },
    KELVINS(listOf("k"), "kelvin", "kelvins", 't') {
        override fun toHubValue(value: Double) = value - 273.15
        override fun fromHubValue(value: Double) = value + 273.15
    },
    NULL(listOf("???"), "???", "???", 't') {
        private val _convRate: Double = 0.0
        override fun toHubValue(value: Double) = fromHubValue(value, _convRate)
        override fun fromHubValue(value: Double) =
            fromHubValue(value, _convRate)
    };

    // methods
    abstract fun toHubValue(value: Double): Double
    abstract fun fromHubValue(value: Double): Double
    fun convert(outputUnit: String) {
        if (isNegativeValue() && (isWeightUnit() || isDistanceUnit())) {
            println(
                "${if (isWeightUnit()) "Weight" else "Length"} shouldn't " +
                        "be negative"
            )
        } else {
            val inputValue = if (isValidUnit(outputUnit)) value else 0.0
            val other = Units.create(inputValue, outputUnit)
            if (isConvertible(other)) {
                other.value = other.fromHubValue(toHubValue(value))
            }
            print(other)
        }
    }

    private fun print(other: Units) {
        if (isNotNull() && other.isNotNull() && isConvertible(other)) {
            println("${formatUnit()} is ${other.formatUnit()}")
        } else {
            if (isNull().xor(other.isNull()) || isNotConvertible(other) || (isNull() && other.isNull())) {
                println("Conversion from $plural to ${other.plural} is impossible")
            }
        }
    }

    private fun isValidUnit(inputUnit: String): Boolean {
        return abbreviations.contains(inputUnit) ||
                inputUnit == singular.lowercase() ||
                inputUnit == plural.lowercase();
    }

    private fun isNull() = name == "NULL"
    private fun isNotNull() = !isNull()
    private fun formatUnit() =
        "$value ${if (value == 1.0) singular else plural}"

    private fun isNegativeValue() = value < 0.0
    private fun isWeightUnit() = typeTag == 'w'
    private fun isDistanceUnit() = typeTag == 'd'
    private fun isConvertible(other: Units) = typeTag == other.typeTag
    private fun isNotConvertible(other: Units) = !isConvertible(other)

    // factory companion object
    companion object Factory {
        fun create(inputValue: Double, inputUnit: String): Units {
            for (enum in Units.values()) {
                if (enum.isValidUnit(inputUnit)) {
                    enum.value = inputValue
                    return enum
                }
            }
            return NULL
        }
    }
}

// TO DO: replace by command design pattern
class Parser(
    private val scanner: Scanner = Scanner(System.`in`),
    var inputValue: Double = 0.0,
    var inputUnit: String = "",
    var outputUnit: String = ""
) {
    fun checkExit(): Boolean {
        if (scanner.hasNext("exit")) {
            scanner.close()
            return true
        }
        return false
    }

    fun parseInput(): Boolean {
        if (scanner.hasNextDouble()) {
            inputValue = scanner.nextDouble()
            if (scanner.hasNext()) {
                inputUnit = parseUnit()
                if (scanner.hasNext()) {
                    scanner.next()
                    if (scanner.hasNext()) {
                        outputUnit = parseUnit()
                        return false
                    }
                }
            }

        }
        println("Parse error")
        return true
    }

    private fun parseUnit(): String {
        val unit = scanner.next().lowercase();
        return unit + if (isDegree(unit)) " " + scanner.next()
            .lowercase() else ""
    }

    private fun isDegree(unit: String) = (unit == "degree" || unit == "degrees")
}

fun main() {
    do {
        print("Enter what you want to convert (or exit): ")
        val parser = Parser()
        // check for exit condition
        val exit = parser.checkExit()
        if (!exit) {
            // parse input from terminal
            val parsingError = parser.parseInput()
            if (!parsingError) {
                // perform unit conversion
                val unitIn = Units.create(parser.inputValue, parser.inputUnit)
                unitIn.convert(parser.outputUnit)
            }
        }
    } while (!exit)
}
