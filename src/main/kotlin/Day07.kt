import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.math.abs
import kotlin.math.min

class Day07 {
    companion object {

        private fun minAlignmentCost(input: List<Int>, cost: (p1: Int, p2: Int) -> Int): Int {
            var minCost = Int.MAX_VALUE
            for (i in input.indices) {
                var alignmentCost = 0
                for (j in input.indices) {
                    if (i != j) {
                        alignmentCost += cost(input[i], input[j])
                    }
                }
                minCost = min(alignmentCost, minCost)
            }
            return minCost
        }

        fun part1(input: List<Int>): Any? = minAlignmentCost(input) { p1, p2 -> abs(p1 - p2) }

        fun part2(input: List<Int>): Any? =
            minAlignmentCost(input) { p1, p2 -> abs(p1 - p2) * (abs(p1 - p2) + 1) / 2 }

    }
}


fun main(args: Array<String>) {
    val input = Path("inputs/day07.txt")
        .readText().split(",").map { it.toInt() }
    println(Day07.part1(input))
    println(Day07.part2(input))
}