package y2024

import Day
import util.Direction
import util.FileReader
import util.Grid

fun main() = Day06.run()

object Day06: Day() {

    private val data = Grid(FileReader.readAsCharLists("y2024/Day06.txt"))

    override fun part1(): Int {
        var guardPos = data.getCoord('^')
        var guardDirection = Direction.UP
        val visitedPositions = mutableSetOf(guardPos to guardDirection)
        while (guardPos.first >= 0 && guardPos.second >= 0) {
            val nextStep = data.getNeighbour(guardPos.second, guardPos.first, guardDirection)
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
                guardPos = data.getNeighbourCoord(guardPos.second, guardPos.first, guardDirection)
                visitedPositions.add(guardPos to guardDirection)
            }
        }
        return visitedPositions.size
    }

    override fun part2(): Int {
        var obstructions = 0
        for (y in data.yIndices) {
            for (x in data.xIndices) {
                if (data.get(y to x) == '.') {
                    var guardPos = data.getCoord('^')
                    var guardDirection = Direction.UP
                    val visitedPositions = mutableSetOf(guardPos to guardDirection)
                    while (guardPos.first >= 0 && guardPos.second >= 0) {
                        val nextStep = data.getNeighbour(guardPos.second, guardPos.first, guardDirection)
                        val nextPos = data.getNeighbourCoord(guardPos.second, guardPos.first, guardDirection)
                        if (nextStep == null) {
                            guardPos = -1 to -1
                        } else if (nextStep == '#' || (nextPos.first == y && nextPos.second == x)) {
                            val oldDirection = guardDirection
                            when (oldDirection) {
                                Direction.UP -> guardDirection = Direction.RIGHT
                                Direction.DOWN -> guardDirection = Direction.LEFT
                                Direction.LEFT -> guardDirection = Direction.UP
                                Direction.RIGHT -> guardDirection = Direction.DOWN
                                else -> null
                            }
                        } else {
                            guardPos = nextPos
                            if (visitedPositions.contains(guardPos to guardDirection)) {
                                obstructions++
                                break
                            } else {
                                visitedPositions.add(guardPos to guardDirection)
                            }
                        }
                    }
                }
            }
        }
        return obstructions
    }
}