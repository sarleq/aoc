package y2023

import Day
import util.FileReader

fun main() = Day16.run()

object Day16: Day() {
    private val data = FileReader.readAsCharArrays("y2023/Day16.txt")

    override fun part1() = energizeMap(Beam(0, 0, 'R'))

    override fun part2(): Int {
        var bestConfig = 0
        for (x in data.indices) {
            var energized = energizeMap(Beam(x, 0, 'R'))
            if (energized > bestConfig) bestConfig = energized
            energized = energizeMap(Beam(x, data[0].indices.last, 'L'))
            if (energized > bestConfig) bestConfig = energized
        }
        for (y in data[0].indices) {
            var energized = energizeMap(Beam(0, y, 'D'))
            if (energized > bestConfig) bestConfig = energized
            energized = energizeMap(Beam(data.indices.last, y, 'U'))
            if (energized > bestConfig) bestConfig = energized
        }
        return bestConfig
    }

    fun formatMap(): MutableList<Coord> {
        val map = mutableListOf<Coord>()
        for (row in data.indices) {
            for (col in data[row].indices) {
                val mirror = if (data[row][col] != '.' ) Mirror(data[row][col]) else null
                map.add(Coord(row, col, mirror))
            }
        }
        return map
    }

    private fun energizeMap(firstBeam: Beam): Int {
        val beams = mutableListOf(firstBeam)
        val map = formatMap()
        while (beams.isNotEmpty()) {
            var inLoop = false
            val beam = beams[0]
            val xRange = IntRange(0, data.indices.last); val yRange = IntRange(0, data[0].indices.last)
            while (beam.x in xRange && beam.y in yRange && !inLoop) {
                val coord = map.first { it.x == beam.x && it.y == beam.y }
                val mirror = coord.mirror
                coord.energized = true
                if (mirror != null) {
                    inLoop = (mirror.hitFromUp && beam.direction == 'D')
                            || (mirror.hitFromDown && beam.direction == 'U')
                            || (mirror.hitFromLeft && beam.direction == 'R')
                            || (mirror.hitFromRight && beam.direction == 'L')

                    when (beam.direction) {
                        'L' -> mirror.hitFromRight = true
                        'R' -> mirror.hitFromLeft = true
                        'D' -> mirror.hitFromUp = true
                        else -> mirror.hitFromDown = true
                    }

                    when (coord.mirror!!.type) {
                        '/' -> {
                            when (beam.direction) {
                                'R' -> beam.direction = 'U'
                                'L' -> beam.direction = 'D'
                                'U' -> beam.direction = 'R'
                                else -> beam.direction = 'L'
                            }
                        }
                        '\\' -> {
                            when (beam.direction) {
                                'R' -> beam.direction = 'D'
                                'L' -> beam.direction = 'U'
                                'U' -> beam.direction = 'L'
                                else -> beam.direction = 'R'
                            }
                        }
                        '-' -> {
                            if (beam.direction == 'U' || beam.direction == 'D') {
                                beam.direction = 'R'
                                beams.add(Beam(beam.x, beam.y, 'L'))
                            }
                        }
                        '|' -> {
                            if (beam.direction == 'L' || beam.direction == 'R') {
                                beam.direction = 'U'
                                beams.add(Beam(beam.x, beam.y, 'D'))
                            }
                        }
                    }
                }

                when (beam.direction) {
                    'R' -> beam.y++
                    'L' -> beam.y--
                    'U' -> beam.x--
                    else -> beam.x++
                }
            }

            beams.removeAt(0)
        }

//        for (x in data.indices) {
//            for (y in data[x].indices) {
//                print(if (map.first{ it.x == x && it.y == y}.energized) '#' else '.')
//            }
//            println(' ')
//        }
//        println(' ')

        return map.filter { it.energized }.size
    }

}

class Coord(val x: Int, val y: Int, var mirror: Mirror?, var energized: Boolean = false)
class Mirror(val type: Char, var hitFromLeft: Boolean = false, var hitFromRight: Boolean = false, var hitFromDown: Boolean = false, var hitFromUp: Boolean = false)
class Beam(var x: Int, var y: Int, var direction: Char)