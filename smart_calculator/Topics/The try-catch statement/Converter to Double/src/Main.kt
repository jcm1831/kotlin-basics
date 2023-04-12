fun convertStringToDouble(input: String): Double {
    return try {
        input.toDouble()
    } catch (e: Exception) {
        0.0
    }
}