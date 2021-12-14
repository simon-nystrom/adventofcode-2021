import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.io.path.Path
import kotlin.io.path.readLines

class Day14 {
    companion object {

        fun polymerize(input: List<String>, steps: Int): BigDecimal {
            var polymerTemplate = input.first().toMutableList()
            val transitions = input.drop(2).map { it.split(" -> ") }
                .associate { it.first() to it.last() }

            val transitionPairs =
                transitions.map { it.key to listOf(it.key.first() + it.value, it.value + it.key.last()) }.toMap()

            var transitionCounts = transitions.toList().associate { it.first to BigDecimal.ZERO }.toMutableMap()

            val windowed = polymerTemplate.windowed(2)

            for (window in windowed) {
                transitionCounts[window.joinToString("")] = transitionCounts[window.joinToString("")]!! + BigDecimal.ONE
            }

            for (i in 0 until steps) {
                var copy = transitionCounts.toMutableMap()
                for (t in transitionCounts) {
                    for (tp in transitionPairs[t.key]!!) {
                        copy[tp] = copy[tp]!! + t.value
                    }
                    copy[t.key] = copy[t.key]!! - t.value
                }
                transitionCounts = copy.toMutableMap()
            }


            val counted = transitionCounts.map {
                listOf(it.key.first() to it.value, it.key.last() to it.value)
            }
                .flatten()
                .groupBy({ it.first }, { it.second })
                // Divide by 2 here since everything is double counted.
                .mapValues { it.value.sumOf { num -> num }.divide(BigDecimal(2)).setScale(0, RoundingMode.CEILING) }
                .toMutableMap()

            val max = counted.maxByOrNull { it.value }!!
            val min = counted.minByOrNull { it.value }!!


            return max.value - min.value
        }

        fun part1(input: List<String>) = polymerize(input, 10)
        fun part2(input: List<String>) = polymerize(input, 40)

    }
}

fun main(args: Array<String>) {
    val input = Path("inputs/day14.txt").readLines()
    println(Day14.part1(input))
    println(Day14.part2(input))


}
