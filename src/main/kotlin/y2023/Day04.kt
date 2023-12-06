package y2023

import Day
import util.FileReader

fun main() = Day04.run()

object Day04: Day() {
    private val data = FileReader.readAsStrings("y2023/Day04.txt")

    override fun part1(): Int {
        var sum = 0
        for (card in data) {
            val (part1, part2) = card.split(" | ")
            val winningNumbers = part1.split(": ")[1].split(" ").mapNotNull { if (it.isNotBlank()) it.toInt() else null }
            val myNumbers = part2.split(" ").mapNotNull { if (it.isNotBlank()) it.toInt() else null }
            val commonNumbers = winningNumbers.intersect(myNumbers.toSet())

            var points = if (commonNumbers.isEmpty()) 0 else 1
            for (i in 2..commonNumbers.size) points *= 2
            sum += points
        }
        return sum //22488
    }

    override fun part2(): Int {
        val cards = hashMapOf<Int, Int>()
        for (i in 1..data.size) cards[i] = 1
        for (card in data) {
            val (part1, part2) = card.split(" | ")
            val cardNumber = part1.split(": ")[0].replace(Regex("\\s+"), " ").split(" ")[1].toInt()
            val winningNumbers = part1.split(": ")[1].split(" ").mapNotNull { if (it.isNotBlank()) it.toInt() else null }
            val myNumbers = part2.split(" ").mapNotNull { if (it.isNotBlank()) it.toInt() else null }
            val copies = winningNumbers.intersect(myNumbers.toSet()).size
            for (i in cardNumber + 1..cardNumber + copies) {
                if (cards.containsKey(i)) cards[i] = cards.getOrDefault(i, 0) + cards[cardNumber]!!
            }
        }
        return cards.values.sum() //7013204
    }
}