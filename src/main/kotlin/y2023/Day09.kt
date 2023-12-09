package y2023

import Day
import util.FileReader

fun main() = Day09.run()

object Day09: Day() {
    val data = FileReader.readAsStrings("y2023/Day09.txt").map { l -> l.split(" ").map { it.toInt() } }

    override fun part1(): List<Int> { //and part2
        val extraValues = mutableListOf<Int>()
        val oldestValues = mutableListOf<Int>()
        for (history in data) {
            val sequences = mutableListOf<MutableList<Int>>()
            sequences.add(history.toMutableList())
            var currentSequence = history.toMutableList()
            while (!currentSequence.all { it == 0 }) {
                val nextSequence = mutableListOf<Int>()
                for (i in 0..<currentSequence.indices.last) {
                    nextSequence.add(currentSequence[i+1] - currentSequence[i])
                }
                sequences.add(nextSequence)
                currentSequence = nextSequence
            }
            var newValue = 0; var prevValue: Int
            var newValuePart2 = 0; var prevValuePart2: Int
            for (sequence in sequences.reversed()) {
                prevValue = newValue
                newValue = sequence.last() + prevValue

                prevValuePart2 = newValuePart2
                newValuePart2 = sequence.first() - prevValuePart2
            }
            extraValues.add(newValue)
            oldestValues.add(newValuePart2)
        }
        return listOf(extraValues.sum(), oldestValues.sum())
    }

    override fun part2() = -1
}