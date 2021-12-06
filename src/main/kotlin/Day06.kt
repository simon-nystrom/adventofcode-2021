import java.math.BigDecimal
import kotlin.io.path.Path
import kotlin.io.path.readText

class Day06 {
    companion object {
        private fun reproduce(input: List<Int>, days: Int): BigDecimal {
            val fishCounts = input.groupingBy { it }.eachCount().mapValues { it.value.toBigDecimal() }.toMutableMap()
            for (i in 0..9) {
                if (fishCounts[i % 9] == null) fishCounts[i % 9] = BigDecimal.ZERO
            }
            var numFish = fishCounts.values.sumOf { it }
            for (i in 1..days) {
                for (j in 0..8) {
                    if (j == 0) {
                        val numAtZero = fishCounts[0]!!
                        fishCounts[7] = numAtZero + fishCounts[7]!!
                        fishCounts[9] = numAtZero
                        numFish += numAtZero
                    }
                    fishCounts[j] = fishCounts[j + 1]!!
                }
            }
            return numFish
        }

        fun part1(input: List<Int>) = reproduce(input, 80)
        fun part2(input: List<Int>) = reproduce(input, 256)
    }
}


fun main(args: Array<String>) {
    val line = Path("inputs/day06.txt")
        .readText().split(",").map { it.toInt() }

    println(Day06.part1(line))
    println(Day06.part2(line))


}