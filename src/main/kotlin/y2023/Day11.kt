package y2023

import Day
import util.*
import util.MathsUtil.calculateManhattanDistance
import util.MathsUtil.transposeCharArray
import java.math.BigInteger

fun main() = Day11.run()

object Day11: Day() {
    private val data = FileReader.readAsCharArrays("y2023/Day11.txt")

    override fun part1(): BigInteger {
        val universe = getUniverse()
        val galaxies = getGalaxies(universe)
        return calculateDistances(galaxies, universe)
    }

    override fun part2(): BigInteger {
        val universe = data.toMutableList()
        val galaxies = getGalaxies(universe)
        return calculateDistances(galaxies, universe, true)
    }

    private fun calculateDistances(galaxies: List<Pair<Int, Int>>, universe: List<CharArray>, part2: Boolean = false): BigInteger {
        var distances = BigInteger.ZERO
        for (i in 0..<galaxies.indices.last) {
            for (j in i+1..galaxies.indices.last) {
                if (galaxies[i] != galaxies[j]) {
                    distances += calculateManhattanDistance(galaxies[i].first, galaxies[i].second, galaxies[j].first, galaxies[j].second).toBigInteger()
                    if (part2) {
                        val emptyRows = getEmptyRowsOrCols(universe, galaxies[i].first, galaxies[j].first)
                        val emptyCols = getEmptyRowsOrCols(transposeCharArray(universe), galaxies[i].second, galaxies[j].second)
                        distances += ((emptyRows + emptyCols) * 1000000.toBigInteger()) - (emptyRows + emptyCols)
                    }
                }
            }
        }
        return distances
    }

    private fun getEmptyRowsOrCols(universe: List<CharArray>, x1: Int, x2: Int): BigInteger {
        var rows = 0
        val start = if (x1 <= x2) x1+1 else x2+1
        val end = if (x1 <= x2) x2 else x1
        for (i in start..<end)
            if (universe[i].all { it == '.' }) rows++
        return rows.toBigInteger()
    }

    private fun expandUniverse(array: List<CharArray>): List<CharArray> {
        val universe = mutableListOf<CharArray>()
        for (line in array) {
            universe.add(line)
            if (line.all { it == '.' }) universe.add(line)
        }
        return universe
    }

    private fun getUniverse(): List<CharArray> {
        var universe = expandUniverse(data)
        universe = transposeCharArray(universe)
        universe = expandUniverse(universe)
        return transposeCharArray(universe)
    }

    private fun getGalaxies(universe: List<CharArray>): List<Pair<Int, Int>> {
        val galaxies = mutableListOf<Pair<Int, Int>>()
        for (x in universe.indices) {
            for (y in universe[x].indices) {
                if (universe[x][y] == '#') {
                    galaxies.add(x to y)
                }
            }
        }
        return galaxies
    }

}