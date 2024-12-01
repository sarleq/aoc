package y2024

import Day
import util.FileReader
import kotlin.math.abs

fun main() = Day01.run()

object Day01: Day() {

    private val file = FileReader.readAsStrings("y2024/Day01.txt")

    override fun part1(): Int {
        val listOne = file.map {
            it.split("   ")[0].toInt()
        }.sorted()
        val listTwo = file.map {
            it.split("   ")[1].toInt()
        }.sorted()
        var distance = 0
        for (i in listOne.indices) {
            distance += abs(listOne[i] - listTwo[i])
        }
        return distance
    }

    override fun part2(): Any {
        val listOne = file.map {
            it.split("   ")[0].toInt()
        }
        val listTwo = file.map {
            it.split("   ")[1].toInt()
        }
        var similarity = 0
        listOne.forEach {
            similarity += it * listTwo.count{i -> i == it}
        }
        return similarity
    }
}