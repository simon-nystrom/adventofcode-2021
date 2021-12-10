import Day10.Companion.errorScore
import kotlin.io.path.Path
import kotlin.io.path.readLines

class Day10 {
    companion object {

        private val m = mapOf('(' to ')', '{' to '}', '[' to ']', '<' to '>')

        fun errorScore(s: String): Int {
            val points = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
            var expectClosing: MutableList<Char> = mutableListOf()
            for (c in s) {
                if (c in m.keys) expectClosing.add(m[c]!!)
                if (c in m.values && expectClosing.removeAt(expectClosing.size - 1) != c)
                    return points[c]!!
            }
            return 0
        }

        private fun completionScore(s: String): Long {
            val points = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)
            var expectClosing: MutableList<Char> = mutableListOf()
            for (c in s) {
                if (c in m.keys) expectClosing.add(m[c]!!)
                if (c in m.values) expectClosing.removeAt(expectClosing.size - 1)
            }
            return expectClosing.reversed().fold(0L) { acc, c -> acc * 5 + points[c]!! }
        }

        fun part1(input: List<String>) =
            input.fold(0) { acc, s -> acc + errorScore(s) }

        fun part2(input: List<String>): Long {
            val scores = input.map { completionScore(it) }.sorted()
            return scores[scores.size / 2]
        }
    }
}

fun main(args: Array<String>) {
    val input = Path("inputs/day10.txt").readLines()
    println(Day10.part1(input))
    println(Day10.part2(input.filter { errorScore(it) == 0 }))
}