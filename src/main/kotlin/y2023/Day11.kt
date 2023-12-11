package y2023

import Day
import util.*
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

    private fun calculateDistances(galaxies: MutableList<Pair<Int, Int>>, universe: MutableList<CharArray>, part2: Boolean = false): BigInteger {
        var distances = BigInteger.ZERO
        for (i in 0..<galaxies.indices.last) {
            for (j in i+1..galaxies.indices.last) {
                if (galaxies[i] != galaxies[j]) {
                    distances += MathsUtil.calculateManhattanDistance(galaxies[i].first, galaxies[i].second, galaxies[j].first, galaxies[j].second).toBigInteger()
                    if (part2) {
                        val emptyRows = getEmptyRowsOrCols(universe, galaxies[i].first, galaxies[j].first)
                        val emptyCols = getEmptyRowsOrCols(transpose(universe), galaxies[i].second, galaxies[j].second)
                        distances += ((emptyRows + emptyCols) * 1000000.toBigInteger()) - (emptyRows + emptyCols)
                    }
                }
            }
        }
        return distances
    }

    private fun getEmptyRowsOrCols(universe: MutableList<CharArray>, x1: Int, x2: Int): BigInteger {
        var rows = 0
        val start = if (x1 <= x2) x1+1 else x2+1
        val end = if (x1 <= x2) x2 else x1
        for (i in start..<end)
            if (universe[i].all { it == '.' }) rows++
        return rows.toBigInteger()
    }

    private fun expandUniverse(array: MutableList<CharArray>): MutableList<CharArray> {
        val universe = mutableListOf<CharArray>()
        for (line in array) {
            universe.add(line)
            if (line.all { it == '.' }) universe.add(line)
        }
        return universe
    }

    private fun transpose(array: MutableList<CharArray>): MutableList<CharArray> {
        val width = array.size
        val height = array[0].size
        val newArray = MutableList(height) { CharArray(width) }
        for (x in 0..<width) {
            for (y in 0..<height) {
                newArray[y][x] = array[x][y]
            }
        }
        return newArray
    }

    private fun getUniverse(): MutableList<CharArray> {
        var universe = expandUniverse(data.toMutableList())
        universe = transpose(universe)
        universe = expandUniverse(universe)
        return transpose(universe)
    }

    private fun getGalaxies(universe: MutableList<CharArray>): MutableList<Pair<Int, Int>> {
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