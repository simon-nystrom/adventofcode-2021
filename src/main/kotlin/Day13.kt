import kotlin.io.path.Path
import kotlin.io.path.readLines

class Day13 {
    companion object {

        fun part1(dots: MutableSet<List<Int>>, folds: List<String>): Any? {
            var firstFold = folds.first()
            var kept: MutableSet<List<Int>> = dots.toMutableSet()
            val (axis, value) = firstFold.split(" ").last().split("=")
            if (axis == "y") kept = foldUp(kept, value.toInt())
            if (axis == "x") kept = foldLeft(kept, value.toInt())

            return kept.size
        }

        fun part2(dots: MutableSet<List<Int>>, folds: List<String>): MutableSet<List<Int>> {

            var kept: MutableSet<List<Int>> = dots.toMutableSet()
            for (fold in folds) {
                val (axis, value) = fold.split(" ").last().split("=")
                if (axis == "y") kept = foldUp(kept, value.toInt())
                if (axis == "x") kept = foldLeft(kept, value.toInt())
            }

            return kept
        }

        private fun foldUp(grid: MutableSet<List<Int>>, foldPoint: Int): MutableSet<List<Int>> {
            val topPoints = grid.filter { it.last() < foldPoint }.toMutableSet()
            val bottomPoints = grid.filter { it.last() > foldPoint }.toMutableSet()
            bottomPoints.forEach { topPoints.add(listOf(it.first(), 2 * foldPoint - it.last())) }
            return topPoints
        }

        private fun foldLeft(grid: MutableSet<List<Int>>, foldPoint: Int): MutableSet<List<Int>> {
            val leftPoints = grid.filter { it.first() < foldPoint }.toMutableSet()
            val rightPoints = grid.filter { it.first() > foldPoint }.toMutableSet()
            rightPoints.forEach { leftPoints.add(listOf(2 * foldPoint - it.first(), it.last())) }
            return leftPoints
        }
    }
}

fun printGrid(dots: MutableSet<List<Int>>) {
    val maxX = dots.maxByOrNull { it.first() }!!.first()
    val maxY = dots.maxByOrNull { it.last() }!!.last()
    for (y in 0..maxY) {
        for (x in 0..maxX) {
            if (listOf(x, y) in dots) {
                print("#")
            } else {
                print(" ")
            }
        }
        println()
    }
}

fun main(args: Array<String>) {
    val input = Path("inputs/day13.txt").readLines()

    val dots =
        input.filter { !it.startsWith("fold") && it != "" }.map { it.split(",").map { n -> n.toInt() } }
            .toMutableSet()
    val folds = input.filterNot { !it.startsWith("fold") }

    println(Day13.part1(dots, folds))
    val res = Day13.part2(dots, folds)
    printGrid(res)

}