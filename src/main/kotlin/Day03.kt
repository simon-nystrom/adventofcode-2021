import kotlin.io.path.Path
import kotlin.io.path.readLines

class Day03 {
    companion object {

        fun part1(): Int {
            val lines = Path("inputs/day03.txt").readLines()

            val (gamma, epsilon) = lines.first().indices
                .map { i -> lines.groupingBy { it[i] }.eachCount() }
                .fold(mutableListOf<Int>()) { acc, map1 ->
                    acc.add(map1['0']!!)
                    acc
                }.map { it > lines.size / 2 }
                .map { if (it) Pair("0", "1") else Pair("1", "0") }
                .reduce { acc, pair -> Pair(acc.first + pair.first, acc.second + pair.second) }


            return gamma.toInt(2) * epsilon.toInt(2)
        }

        fun part2(): Int {
            val lines = Path("inputs/day03.txt").readLines()

            var ox = 0
            var co2 = 0

            var filtered = lines.toList()
            lines.first().forEachIndexed { index, _ ->
                filtered = filtered.filter { s ->
                    val counts = filtered.groupingBy { it[index] }.eachCount()
                    if (counts['0'] == counts['1']) s[index] == '1'
                    else s[index] == (counts.maxByOrNull { it.value }?.key)
                }
                ox = filtered.first().toInt(2)
            }

            filtered = lines.toList()
            lines.first().forEachIndexed { index, _ ->
                filtered = filtered.filter { s ->
                    val counts = filtered.groupingBy { it[index] }.eachCount()
                    if (counts['0'] == counts['1']) s[index] == '0'
                    else s[index] == (counts.minByOrNull { it.value }?.key)
                }
                co2 = filtered.first().toInt(2)
            }

            return ox * co2
        }
    }
}


fun main(args: Array<String>) {
    println(Day03.part1())
    println(Day03.part2())
}