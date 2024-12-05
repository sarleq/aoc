package y2024

import Day
import util.Direction
import util.FileReader
import util.Grid

fun main() = Day04.run()

object Day04: Day() {

    private val grid = Grid(FileReader.readAsCharLists("y2024/Day04.txt"))

    override fun part1(): Int {
        var xmas = 0
        for (y in grid.yIndices) {
            for (x in grid.xIndices) {
                if (grid.get(y to x) == 'X') {
                    xmas += findXmas(x, y)
                }
            }
        }
        return xmas
    }

    override fun part2(): Any {
        var xmas = 0
        for (y in grid.yIndices) {
            for (x in grid.xIndices) {
                if (grid.get(y to x) == 'A') {
                    xmas += (if (findMas(x, y)) 1 else 0)
                }
            }
        }
        return xmas
    }

    private fun findXmas(x: Int, y: Int): Int {
        var times = 0
        val moves = listOf("U", "D", "R", "L", "UR", "UL", "DR", "DL")
        for (move in moves) {
            var nextPos = findNextLetter(x, y, 'M', move)
            if (nextPos.first != -1) {
                nextPos = findNextLetter(nextPos.first, nextPos.second, 'A', move)
                if (nextPos.first != -1) {
                    if (findNextLetter(nextPos.first, nextPos.second, 'S', move).first != -1) {
                        times++
                    }
                }
            }
        }
        return times
    }

    private fun findMas(x: Int, y: Int): Boolean {
        return ( (grid.getNeighbour(x, y, Direction.UPRIGHT) == 'M' && grid.getNeighbour(x, y, Direction.DOWNLEFT) == 'S')
            || (grid.getNeighbour(x, y, Direction.UPRIGHT) == 'S' && grid.getNeighbour(x, y, Direction.DOWNLEFT) == 'M' ) )
        && ( (grid.getNeighbour(x, y, Direction.UPLEFT) == 'M' && grid.getNeighbour(x, y, Direction.DOWNRIGHT) == 'S')
                || (grid.getNeighbour(x, y, Direction.UPLEFT) == 'S' && grid.getNeighbour(x, y, Direction.DOWNRIGHT) == 'M') )
    }

    private fun findNextLetter(x: Int, y: Int, letter: Char, move: String): Pair<Int, Int> {
        when (move) {
            "U" -> if (y > 0 && grid.get(y-1 to x) == letter) return x to y-1
            "D" -> if (y < grid.maxY && grid.get(y+1 to x) == letter) return x to y+1
            "L" -> if (x > 0 && grid.get(y to x-1) == letter) return x-1 to y
            "R" -> if (x < grid.maxX && grid.get(y to x+1) == letter) return x+1 to y
            "UL" -> if (y > 0 && x > 0 && grid.get(y-1 to x-1) == letter) return x-1 to y-1
            "UR" -> if (y > 0 && x < grid.maxX && grid.get(y-1 to x+1) == letter) return x+1 to y-1
            "DL" -> if (y < grid.maxY && x > 0 && grid.get(y+1 to x-1) == letter) return x-1 to y+1
            "DR" -> if (y < grid.maxY && x < grid.maxX && grid.get(y+1 to x+1) == letter) return x+1 to y+1
        }
        return -1 to -1
    }

}