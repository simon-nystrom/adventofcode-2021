import kotlin.io.path.Path
import kotlin.io.path.readLines

class Day08 {
    companion object {

        fun part1(input: List<String>): Int =
            input.map { """.* [|] (\w+) (\w+) (\w+) (\w+)""".toRegex().find(it)?.destructured?.toList() }
                .flatMap { it!! }.count { it.length in setOf(3, 4, 7, 2) }

        fun part2(input: List<String>): Int {
            val input = input.map {
                """(\w+) (\w+) (\w+) (\w+) (\w+) (\w+) (\w+) (\w+) (\w+) (\w+) [|] (\w+) (\w+) (\w+) (\w+)""".toRegex()
                    .find(it)?.destructured?.toList()
            }.map { Pair(it!!.take(10), it!!.takeLast(4)) }

            var sum = 0

            for (line in input) {
                val digits = line.first.sortedBy { it.length }
                val output = line.second

                val one = digits[0]
                val seven = digits[1]
                val four = digits[2]
                val eight = digits[9]
                val fiveSegmentDigits = listOf(digits[3], digits[4], digits[5])
                val sixSegmentDigits = listOf(digits[6], digits[7], digits[8])
                val nine = sixSegmentDigits.single { it.toSet().containsAll(four.toSet()) }
                val three = fiveSegmentDigits.single { it.toSet().containsAll(one.toSet()) }
                val five = fiveSegmentDigits.single { it != three && nine.toSet().containsAll(it.toSet()) }
                val two = fiveSegmentDigits.single { it != five && it != three }
                val six = sixSegmentDigits.single { it != nine && it.toSet().containsAll(five.toSet()) }
                val zero = sixSegmentDigits.single { it != six && it != nine }

                val list = listOf(zero, one, two, three, four, five, six, seven, eight, nine)
                val values = (0..9).groupBy({ list[it].toSortedSet() }, { it })

                val value = output.map { values[it.toSortedSet()]!!.single() }.joinToString("").toInt()
                sum += value
            }

            return sum

        }

    }
}

fun main(args: Array<String>) {
    val input = Path("inputs/day08.txt").readLines()
    println(Day08.part1(input))
    println(Day08.part2(input))
}