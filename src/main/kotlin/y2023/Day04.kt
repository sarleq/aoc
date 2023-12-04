package y2023

import Day
import util.FileReader

fun main() = Day04.run()

object Day04: Day() {
    private val data = FileReader.readAsStrings("y2023/Day04.txt")
//    private val data = arrayOf(
//        "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
//        "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
//        "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
//        "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
//        "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
//        "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"
//    )

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
        return sum
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
        return cards.values.sum()
    }
}