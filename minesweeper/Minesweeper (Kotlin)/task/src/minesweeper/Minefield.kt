package minesweeper

import kotlin.random.Random

class Minefield(
    private val sizeX: Int,
    private val sizeY: Int
) {
    private val cells = Array(sizeX) { Array(sizeY) { Cell() } }
    private var numMines: Int
    private var isUnexplored = true

    init {
        // generate cells with position
        cells.forEachIndexed { indexY, cellRow ->
            cellRow.forEachIndexed { indexX, cell ->
                cell.apply {
                    x = indexX
                    y = indexY
                }
            }
        }
        // get number of mines
        print("How many mines do you want on the field? ")
        numMines = readln().toInt()
    }

    fun display() {
        println(" |${(1..sizeX).toList().joinToString("")}|")
        println("-|${"-".repeat(sizeX)}|")
        cells.forEachIndexed { indexX, cellRow ->
            print("${indexX + 1}|")
            cellRow.forEach { print(it) }
            println("|")
        }
        println("-|${"-".repeat(sizeX)}|")
    }

    fun markCell(x: Int, y: Int) {
        if (cells[y][x].isMarked()) {
            cells[y][x].makeUnmarked()
        } else if (cells[y][x].isUnmarked()) {
            cells[y][x].makeMarked()
        }
        display()
    }

    fun exploreCell(x: Int, y: Int): Boolean {
        return if (cells[y][x].isMine()) {
            cells.flatten().forEach { it.showMine() }
            display()
            println("You stepped on a mine and failed!")
            true
        } else {
            if (isUnexplored) {
                // generate mines before first exploration
                repeat(numMines) { randomSafeCell(x,y).makeMine() }
                // calculate number of mines around empty cells
                determineNearestNeighbors()
                // minefield is now explored
                isUnexplored = false
            }
            cells[y][x].explore()
            display()
            false
        }
    }

    fun foundAllMines(): Boolean {
        // check whether all mines are marked
        val marked = cells.flatten().count { it.isMarked() }
        val markedAndMine = cells.flatten().count() { it.isMarkedMine() }
        // check whether all unexplored cells are mines
        val unexplored = cells.flatten().count { it.isUnexplored() }
        val unexploredAndMine = cells.flatten().count { it.isUnexploredMine() }
        return (marked == numMines && markedAndMine == numMines) ||
                (unexplored == unexploredAndMine)
    }

    private fun randomSafeCell(freeX : Int, freeY : Int): Cell {
        while (true) {
            val (x, y) = Pair(
                Random.nextInt(0, sizeX),
                Random.nextInt(0, sizeY)
            )
            val remainsFree = (x == freeX && y == freeY)
            if (cells[y][x].isMine() || remainsFree) {
                continue
            } else {
                return cells[y][x]
            }
        }
    }

    private fun determineNearestNeighbors() {
        for (cell in cells.flatten()) {
            for (deltaX in -1..+1) {
                for (deltaY in -1..+1) {
                    val (x, y) = Pair(cell.x + deltaX, cell.y + deltaY)
                    if (isWithinMinefield(x, y)) cell.add(Neighbor(cells[y][x]))
                }
            }
        }
    }

    private fun isWithinMinefield(x: Int, y: Int): Boolean {
        return ((x in 0 until sizeX) && (y in 0 until sizeY))
    }

}