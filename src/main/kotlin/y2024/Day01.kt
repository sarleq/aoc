package y2024

import Day
import util.FileReader
import kotlin.math.abs

fun main() = Day01.run()

object Day01: Day() {

    private val data = FileReader.readAsStrings("y2024/Day01.txt")

    override fun part1(): Int {
        val listOne = data.map {
            it.split("   ")[0].toInt()
        }.sorted()
        val listTwo = data.map {
            it.split("   ")[1].toInt()
        }.sorted()
        var distance = 0
        for (i in listOne.indices) {
            distance += abs(listOne[i] - listTwo[i])
        }
        return distance
    }

    override fun part2(): Int {
        val listOne = data.map {
            it.split("   ")[0].toInt()
        }
        val listTwo = data.map {
            it.split("   ")[1].toInt()
        }
        var similarity = 0
        listOne.forEach {
            similarity += it * listTwo.count{i -> i == it}
        }
        return similarity
    }
}