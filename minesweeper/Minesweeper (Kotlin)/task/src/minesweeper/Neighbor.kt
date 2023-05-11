package minesweeper

class Neighbor(private val cell : Cell = Cell()) {
    fun isMine() = cell.isMine()
    fun explore() {
        if (cell.isNotMine() && cell.isNotExplored()) cell.explore()
    }
}