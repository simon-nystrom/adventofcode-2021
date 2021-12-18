import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.max
import kotlin.math.sign

class Day17 {
    companion object {

        data class Projectile(var x: Int, var y: Int, var xVel: Int, var yVel: Int) {
            fun step() {
                x += xVel
                y += yVel
                xVel -= xVel.sign
                yVel -= 1
            }
        }


        fun fire(xVel: Int, yVel: Int, minX: Int, maxX: Int, minY: Int, maxY: Int): Int {
            val p = Projectile(0, 0, xVel, yVel)
            var highestY = 0
            while (p.y >= minY) {
                if (p.x < minX && p.xVel <= 0) break;
                highestY = max(highestY, p.y)
                p.step()
                if (p.x in minX..maxX && p.y in minY..maxY) {
                    hits.add(Pair(xVel, yVel))
                    return highestY
                }
            }
            return 0
        }

        fun part1(minX: Int, maxX: Int, minY: Int, maxY: Int): Int {
            var highestY = 0
            for (x in 0 .. 1000) {
                for (y in -1000 until 1000) {
                    highestY = max(fire(x, y, minX, maxX, minY, maxY), highestY)
                }
            }
            return highestY
        }

        var hits = mutableSetOf<Pair<Int, Int>>()
        fun part2(minX: Int, maxX: Int, minY: Int, maxY: Int): Int {
            for (x in 0 .. maxX) {
                for (y in -1000 until 1000) {
                    fire(x, y, minX, maxX, minY, maxY)
                }
            }
            return hits.size
        }

    }
}

fun main(args: Array<String>) {
    val input = Path("inputs/day17.txt").readLines().first()
    val split = input.split("..")
    val minX = split.first().split("=").last().toInt()
    val maxX = split[1].split(",").first().toInt()
    val minY = split[1].split(",").last().split("=").last().toInt()
    val maxY = split.last().toInt()

    println(Day17.part1(minX, maxX, minY, maxY))
    println(Day17.part2(minX, maxX, minY, maxY))
}