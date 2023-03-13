fun polynomial(x: Int, c: Int, b: Int = 0, a: Int = 0): Int {
    return when {
        a == 0 && b == 0 -> c
        a == 0 -> b * x + c
        else -> a * x * x + b * x + c
    }
}