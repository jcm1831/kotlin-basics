package minesweeper

enum class ExplorationStates { EXPLORED, UNEXPLORED, MARKED }
enum class MineStates { MINE, FREE }


class Cell(
    var x: Int = 0,
    var y: Int = 0,
    private var symbol: Char = '.'
) {
    private val neighbors = arrayListOf<Neighbor>()
    private var explorationState = ExplorationStates.UNEXPLORED
    private var mineState = MineStates.FREE

    fun showMine() {
        if (isMine()) {
            symbol = 'X'
            explorationState = ExplorationStates.EXPLORED
        }
    }

    fun explore() {
        explorationState = ExplorationStates.EXPLORED
        symbol = when (val numMines = countNearestNeighborMines()) {
            0 -> {
                neighbors.forEach { it.explore() }
                '/'
            }

            else -> numMines.digitToChar()
        }
    }

    fun isNotExplored() = explorationState != ExplorationStates.EXPLORED
    fun isUnexplored() = explorationState == ExplorationStates.UNEXPLORED
    fun isUnexploredMine() =
        explorationState == ExplorationStates.UNEXPLORED && mineState == MineStates.MINE
    fun isMine() = mineState == MineStates.MINE
    fun isNotMine() = !isMine()
    fun isMarkedMine() =
        mineState == MineStates.MINE && explorationState == ExplorationStates.MARKED

    fun isMarked() = explorationState == ExplorationStates.MARKED
    fun isUnmarked() = !isMarked()
    fun makeMine() {
        mineState = MineStates.MINE
        explorationState = ExplorationStates.UNEXPLORED
    }

    fun makeMarked() {
        symbol = '*'
        explorationState = ExplorationStates.MARKED
    }

    fun makeUnmarked() {
        symbol = '.'
        explorationState = ExplorationStates.UNEXPLORED
    }

    fun add(neighbor: Neighbor) = neighbors.add(neighbor)

    private fun countNearestNeighborMines() = neighbors.count { it.isMine() }

    override fun toString() = symbol.toString()
}