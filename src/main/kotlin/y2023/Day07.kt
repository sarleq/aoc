package y2023

import Day
import util.FileReader

fun main() = Day07.run()

object Day07: Day() {
    private val data = FileReader.readAsStrings("y2023/Day07.txt").map { it.split(" ")[0] to it.split(" ")[1].toInt() }

    override fun part1() = data.map { Hand(it.first, it.second, false) }.sortedWith(handComparator).mapIndexed { index, hand -> hand.bid * (index + 1) }.sumOf { it }

    override fun part2() = data.map { Hand(it.first, it.second, true) }.sortedWith(handComparator).mapIndexed { index, hand -> hand.bid * (index + 1) }.sumOf { it }

    private val handComparator = object: Comparator<Hand> {
        override fun compare(hand1: Hand, hand2: Hand): Int {
            if (hand1.type != hand2.type) return hand1.type - hand2.type
            else {
                for (i in hand1.hand.indices) {
                    if (hand1.hand[i] != hand2.hand[i]) return hand1.hand[i] - hand2.hand[i]
                }
            }
            return 0
        }
    }

}

class Hand(hand: String, val bid: Int, val part2: Boolean) {
    val type = calculateHand(hand)
    val hand = hand.split("").filter { it.isNotBlank() }.map { when (it) {
        "T" -> 10
        "J" -> if (part2) 1 else 11
        "Q" -> 12
        "K" -> 13
        "A" -> 14
        else -> it.toInt()
    } }

    private fun calculateHand(hand: String): Int {
        val uniqueCards = hand.toCharArray().distinct().count()
        val cardsMap = mutableMapOf<Char, Int>()
        for (card in hand.toCharArray()) cardsMap[card] = cardsMap.getOrDefault(card, 0) + 1
        val type = when {
            cardsMap.any { it.value == 5 } -> 6 // five of a kind
            cardsMap.any { it.value == 4 } -> 5 // four of a kind
            uniqueCards == 2 && cardsMap.any { it.value == 3 } -> 4 // full house
            uniqueCards == 3 && cardsMap.any { it.value == 3 } -> 3 // three of a kind
            uniqueCards == 3 && cardsMap.any { it.value == 2 } -> 2 // two pairs
            uniqueCards == 4 -> 1 // one pair
            else -> 0 // high card
        }
        return if (part2) when {
            type == 5 && countJokers(hand) > 0 -> 6
            type == 4 && countJokers(hand) > 0 -> 6
            type == 3 && countJokers(hand) > 0 -> 5
            type == 2 && countJokers(hand) == 1 -> 4
            type == 2 && countJokers(hand) == 2 -> 5
            type == 1 && countJokers(hand) > 0 -> 3
            type == 0 && countJokers(hand) == 1 -> 1
            else -> type
        } else type
    }

    private fun countJokers(hand: String) = hand.length - hand.replace("J", "").length
}