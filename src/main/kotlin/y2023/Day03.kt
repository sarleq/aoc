package y2023

import Day
import util.FileReader

fun main() = Day03.run()

object Day03: Day() {
    private val data = FileReader.readAsStrings("y2023/Day03.txt")

    override fun part1(): Int {
        var sum = 0
        for (x in data.indices) {
            val numbers =  Regex("[0-9]+").findAll(data[x])
            for (num in numbers) {
                val start = if (num.range.first == 0) num.range.first else num.range.first-1
                val end = if (num.range.last == data[0].length-1) num.range.last else num.range.last+1
                if (findSymbol(x, IntRange(start, end))) sum += num.value.toInt()
            }
        }
        return sum
    }

    private fun findSymbol(x: Int, range: IntRange): Boolean {
        val reg = Regex("[^0-9|.]")
        //left
        if (reg.matches(data[x].toCharArray()[range.first].toString())) return true
        //right
        if (reg.matches(data[x].toCharArray()[range.last].toString())) return true
        for (y in range) {
                //top
                if (x > 0 && reg.matches(data[x-1].toCharArray()[y].toString())) return true
                //bottom
                if (x < data.size-1 && reg.matches(data[x+1].toCharArray()[y].toString())) return true
        }
        return false
    }

    override fun part2(): Any {
        val gears = mutableMapOf<String, MutableList<Int>>()
        for (x in data.indices) {
            val numbers =  Regex("[0-9]+").findAll(data[x])
            for (num in numbers) {
                val start = if (num.range.first == 0) num.range.first else num.range.first-1
                val end = if (num.range.last == data[0].length-1) num.range.last else num.range.last+1
                for (coord in findGears(x, IntRange(start, end)))
                    gears.getOrPut(coord) { mutableListOf() }.add(num.value.toInt())
            }
        }
        return gears.values.sumOf { if (it.size == 2) it[0] * it[1] else 0 }
    }

    private fun findGears(x: Int, range: IntRange): List<String> {
        val coordinates = mutableListOf<String>()
        //left
        if (data[x].toCharArray()[range.first] == '*') coordinates.add(x.toString() + "," + range.first)
        //right
        if (data[x].toCharArray()[range.last] == '*') coordinates.add(x.toString() + "," + range.last)
        for (y in range) {
            //top
            if (x > 0 && data[x-1].toCharArray()[y] == '*') coordinates.add((x-1).toString() + "," + y)
            //bottom
            if (x < data.size-1 && data[x+1].toCharArray()[y] == '*') coordinates.add((x+1).toString() + "," + y)
        }
        return coordinates
    }

}