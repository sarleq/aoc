package y2023

import Day
import util.FileReader

fun main() = Day10.run()

object Day10: Day() {
    val data = FileReader.readAsStrings("y2023/Day10.txt").map { it.toCharArray() }
//    val data = arrayOf(
//            "7-F7-",
//            ".FJ|7",
//            "SJLL7",
//            "|F--J",
//            "LJ.LJ").map { it.toCharArray() }

    override fun part1(): Int {
        val pipes = mutableListOf<Pipe>()
        val startX = data.indexOfFirst { it.contains('S') }
        val startY = data.find { it.contains('S') }!!.indexOfFirst { it == 'S' }
        pipes.add(Pipe(startX, startY, null))
        // for the 1st one, we don't know where to look
        var nextPipe = Pipe(-1, -1, null)
        if (startX > 0 && Regex("[|7F]").matches(data[startX - 1][startY].toString())) //up?
            nextPipe = Pipe(startX - 1, startY, findOut(data[startX - 1][startY], 'D'))
        else if (startY > 0 && Regex("[-LF]").matches(data[startX][startY - 1].toString())) //left?
            nextPipe = Pipe(startX, startY - 1, findOut(data[startX][startY - 1], 'R'))
        else if (startY < data[0].indices.last && Regex("[-J7]").matches(data[startX][startY + 1].toString())) //right?
            nextPipe = Pipe(startX, startY + 1, findOut(data[startX][startY + 1], 'L'))
        else if (startX < data.indices.last() && Regex("[|LJ]").matches(data[startX + 1][startY].toString())) //down?
            nextPipe = Pipe(startX + 1, startY, findOut(data[startX - 1][startY], 'U'))
        pipes.add(nextPipe)

        // easier now
        nextPipe = findNextPipe(nextPipe)
        while (data[nextPipe.x][nextPipe.y] != 'S') {
            pipes.add(nextPipe)
            nextPipe  = findNextPipe(nextPipe)
        }
        return pipes.size / 2
    }

    override fun part2(): Any {
        return -1
    }

    private fun findOut(pipe: Char, entrance: Char): Char {
        when (entrance) {
            'U' -> {
                return when (pipe) {
                    '|' -> 'D'
                    'L' -> 'R'
                    'J' -> 'L'
                    else -> 'E'
                }
            }
            'L' -> {
                return when (pipe) {
                    '-' -> 'R'
                    'J' -> 'U'
                    '7' -> 'D'
                    else -> 'E'
                }

            }
            'R' -> {
                return when (pipe) {
                    '-' -> 'L'
                    'L' -> 'U'
                    'F' -> 'D'
                    else -> 'E'
                }
            }
            else -> {
                return when (pipe) {
                    '|' -> 'U'
                    '7' -> 'L'
                    'F' -> 'R'
                    else -> 'E'
                }
            }
        }
    }

    private fun findNextPipe(pipe: Pipe): Pipe {
        return when(pipe.out) {
            'U' -> Pipe(pipe.x - 1, pipe.y, findOut(data[pipe.x - 1][pipe.y], 'D'))
            'L' -> Pipe(pipe.x, pipe.y - 1, findOut(data[pipe.x][pipe.y - 1], 'R'))
            'R' -> Pipe(pipe.x, pipe.y + 1, findOut(data[pipe.x][pipe.y + 1], 'L'))
            else -> Pipe(pipe.x + 1, pipe.y, findOut(data[pipe.x + 1][pipe.y], 'U'))
        }
    }
}

class Pipe(val x: Int, val y: Int, val out: Char?)