import kotlin.math.hypot

fun perimeter(
    x1: Double, y1: Double,
    x2: Double, y2: Double,
    x3: Double, y3: Double,
    x4: Double = x1, y4: Double = y1
): Double {
    val v1 = hypot(x2 - x1, y2 - y1)
    val v2 = hypot(x3 - x2, y3 - y2)
    val v3 = hypot(x4 - x3, y4 - y3)
    val v4 = hypot(x1 - x4, y1 - y4)
    return v1 + v2 + v3 + v4
}

/*
fun main() {
    println(perimeter(0.0, 0.0, 12.0, 0.0, 0.0 ,5.0, ))
}
*/