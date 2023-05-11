package minesweeper

fun main() {
    // set-up minefield
    val minefield = Minefield(9, 9)
    minefield.display()
    // enter game loop
    while (true) {
        print("Set/unset mine marks or claim a cell as free: ")
        val (x, y, command) = with(readln()) {
            val (x, y, command) = split(" ")
            Triple(x.toInt() - 1, y.toInt() - 1, command)
        }
        when (command) {
            "mine" -> minefield.markCell(x, y)
            "free" -> {
                if (minefield.exploreCell(x, y)) break else continue
            }

            else -> {
                println("Invalid command!")
                continue
            }
        }

        if (minefield.foundAllMines()) {
            println("Congratulations! You found all the mines!")
            break
        } else {
            continue
        }

    }
}