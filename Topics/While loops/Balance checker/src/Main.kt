import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    var balance = readln().toInt()
    var uncoveredExpense = 0
    while (scanner.hasNextInt()) {
        val purchase = scanner.nextInt()
        if (purchase <= balance) {
            balance -= purchase
        } else {
            uncoveredExpense += purchase
        }
    }
    if (balance >= 0 && uncoveredExpense == 0) {
        println("Thank you for choosing us to manage your account! Your balance " +
                "is $balance.")
    } else {
        println("Error, insufficient funds for the purchase. Your balance is " +
                "$balance, but you need $uncoveredExpense.")
    }
}