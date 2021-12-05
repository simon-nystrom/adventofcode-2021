import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.abs

class Day05 {
    companion object {

        private fun delta(startX: Int, startY: Int, endX: Int, endY: Int): Pair<Int, Int> =
            Pair(endX.compareTo(startX), endY.compareTo(startY))

        private fun findVents(lines: List<List<Int>>): Int {
            val grid = (0 until 1000).map { (0 until 1000).map { 0 }.toMutableList() }
            for (line in lines) {
                var (x1, y1, x2, y2) = line
                val d = delta(x1, y1, x2, y2)
                for (i in 0 until maxOf(abs(x1 - x2), abs(y1 - y2)) + 1) {
                    grid[y1][x1]++
                    x1 += d.first
                    y1 += d.second
                }
            }
            return grid.sumOf { row -> row.count { it >= 2 } }
        }

        fun part1(lines: List<List<Int>>) =
            findVents(lines.filter { it[0] == it[2] || it[1] == it[3] })

        fun part2(lines: List<List<Int>>) =
            findVents(lines)

    }
}


fun main(args: Array<String>) {
    val lines =
        Path("inputs/day05.txt")
            .readLines()
            .map {
                """(\d+),(\d+) -> (\d+),(\d+)""".toRegex()
                    .find(it)!!.destructured.toList()
                    .map { it.toInt() }
            }

    println(Day05.part1(lines))
    println(Day05.part2(lines))
}