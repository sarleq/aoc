package y2023

import Day
import util.FileReader

fun main() = Day15.run()

object Day15: Day() {
    private val data = FileReader.readFile("y2023/Day15.txt").split(",").map { it.toCharArray() }

    override fun part1(): Int {
        var sum = 0
        for (step in data) {
            var hashValue = 0
            step.forEach { hashValue = (hashValue + it.code) * 17 % 256 }
            sum += hashValue
        }
        return sum
    }

    override fun part2(): Int {
        val boxes = mutableListOf<MutableList<Lens>>()
        for (i in 0..255) boxes.add(mutableListOf())
        for (step in data) {
            var label: String; var focalLength = -1
            if (step.contains('=')) {
                label = String(step).split("=")[0]
                focalLength = String(step).split("=")[1].toInt()
            } else label = String(step).split("-")[0]


            var boxIdx = 0
            label.forEach { boxIdx = (boxIdx + it.code) * 17 % 256 }
            val lensIdx = boxes[boxIdx].indexOfFirst { it.label == label }

            if (focalLength != -1)
                if (lensIdx == -1) boxes[boxIdx].add(Lens(label, focalLength)) else boxes[boxIdx][lensIdx].focalLength = focalLength
            else if (lensIdx != -1) boxes[boxIdx].removeAt(lensIdx)
        }

        var focusingPower = 0
        boxes.forEachIndexed { boxIdx, lenses ->
            lenses.forEachIndexed { lensIdx, lens ->
                focusingPower += (boxIdx+1) * (lensIdx+1) * lens.focalLength
            }
        }
        return focusingPower
    }

}

class Lens(val label: String, var focalLength: Int)