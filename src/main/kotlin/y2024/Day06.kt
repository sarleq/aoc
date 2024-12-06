package y2024

import util.Direction
import util.FileReader
import util.Grid

fun main() = Day06.run()

object Day06: Day() {

    private val data = Grid(FileReader.readAsCharLists("y2024/Day06_test.txt"))

    override fun part1(): Int {
        var guardPos = data.getCoord('^')
        var guardDirection = Direction.UP
        val visitedPositions = mutableSetOf<Pair<Int, Int>>(guardPos)
        while (guardPos.first >= 0 && guardPos.second >= 0) {
            val nextStep = data.getNeighbour(guardPos.first, guardPos.second, guardDirection)
            if (nextStep == null) {
                guardPos = -1 to -1
            } else if (nextStep == '#') {
                val oldDirection = guardDirection
                when (oldDirection) {
                    Direction.UP -> guardDirection = Direction.RIGHT
                    Direction.DOWN -> guardDirection = Direction.LEFT
                    Direction.LEFT -> guardDirection = Direction.UP
                    Direction.RIGHT -> guardDirection = Direction.DOWN
                    else -> null
                }
            } else {
                guardPos = data.getNeighbourCoord(guardPos.first, guardPos.second, guardDirection)
            }
        }
        return visitedPositions.size
    }

    override fun part2(): Any {
        TODO("Not yet implemented")
    }
}