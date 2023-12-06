package y2023

import Day
import java.math.BigInteger

fun main() = Day06.run()

object Day06: Day() {
    private val data = listOf(41 to 244, 66 to 1047, 72 to 1228, 66 to 1040)
    //private val data = listOf(7 to 9, 15 to 40, 30 to 200)
    private val time = BigInteger("41667266")
    private val record = BigInteger("244104712281040")

    override fun part1(): Any {
        val races = mutableListOf<Int>()
        for ((time, record) in data) {
            var records = 0
            for (i in 0..time) {
                val distance = (time - i) * i
                if (distance > record) records++
            }
            races.add(records)
        }
        return races.reduce { acc, el -> acc * el }
    }

    override fun part2(): Any {
        var records = 0
        var i = BigInteger.ZERO
        while (i <= time) {
            val distance = (time - i) * i
            if (distance > record) records++
            i += BigInteger.ONE
        }
        return records
    }

}