import kotlin.io.path.Path
import kotlin.io.path.readLines

class Day16 {
    companion object {

        var versionSum = 0

        fun createPacket(bits: String): Packet {
            val version = bits.substring(0, 3)
            val typeID = bits.substring(3, 6)
            versionSum += version.toInt(2)
            return if (typeID == "100") LiteralPacket(bits) else OperatorPacket(bits)
        }

        abstract class Packet(bits: String) {
            val version = bits.substring(0, 3)
            val typeID = bits.substring(3, 6)
            abstract val payload: String
            abstract fun parse(): Pair<Int, Long>
        }

        data class LiteralPacket(val bits: String) : Packet(bits) {
            override val payload = bits.substring(6)
            override fun parse(): Pair<Int, Long> {
                var numGroups = 0
                val res = buildString {
                    for (i in payload.indices step 5) {
                        numGroups++
                        append(payload.substring(i + 1, i + 5))
                        if (payload[i] == '0') {
                            break
                        }
                    }
                }
                return Pair(version.length + typeID.length + res.length + numGroups, res.toLong(2))
            }
        }

        fun determineOperator(typeID: String): (Long, Long) -> Long {
            return { a, b ->
                when (typeID) {
                    "000" -> a + b
                    "001" -> a * b
                    "010" -> minOf(a, b)
                    "011" -> maxOf(a, b)
                    "101" -> if (a > b) 1 else 0
                    "110" -> if (a < b) 1 else 0
                    "111" -> if (a == b) 1 else 0
                    else -> throw IllegalArgumentException("Unrecognized typeID: $typeID")
                }
            }
        }

        data class OperatorPacket(val bits: String) : Packet(bits) {
            override val payload = bits.substring(7)
            private val lengthTypeID = bits[6]
            override fun parse(): Pair<Int, Long> {
                var length = 0
                var value = 0L
                var valueInitialized = false
                val op = determineOperator(typeID)

                if (lengthTypeID == '0') {
                    val numBitsInSubPackets = payload.substring(0, 15).toInt(2)
                    while (length < numBitsInSubPackets) {
                        val subpacket = createPacket(payload.substring(15 + length))
                        val parsed = subpacket.parse()
                        length += parsed.first
                        if (!valueInitialized) {
                            value = parsed.second
                            valueInitialized = true
                        } else {
                            value = op(value, parsed.second)
                        }
                    }
                    length += 16 + version.length + typeID.length
                } else {
                    length = 11
                    val numSubPackets = payload.substring(0, length).toInt(2)
                    for (i in 0 until numSubPackets) {
                        val subpacket = createPacket(payload.substring(length))
                        val parsed = subpacket.parse()
                        length += parsed.first
                        if (!valueInitialized) {
                            value = parsed.second
                            valueInitialized = true
                        } else {
                            value = op(value, parsed.second)
                        }
                    }
                    length += 1 + version.length + typeID.length
                }

                return Pair(length, value)
            }
        }


        fun parse(packet: String) {
            val p = createPacket(packet)
            println(p)

            val parsed = p.parse()
            println(parsed)
        }


        fun part1(input: String): Int {
            val p = createPacket(input)
            p.parse()
            return versionSum
        }

        fun part2(input: String): Long {
            val p = createPacket(input)
            return p.parse().second
        }

    }
}

fun main(args: Array<String>) {
    val input = Path("inputs/day16.txt").readLines().first()
    val s = buildString {
        for (c in input) {
            append(encoding[c])
        }
    }
    println(Day16.part1(s))
    println(Day16.part2(s))
}


private val encoding = mapOf(
    '0' to "0000",
    '1' to "0001",
    '2' to "0010",
    '3' to "0011",
    '4' to "0100",
    '5' to "0101",
    '6' to "0110",
    '7' to "0111",
    '8' to "1000",
    '9' to "1001",
    'A' to "1010",
    'B' to "1011",
    'C' to "1100",
    'D' to "1101",
    'E' to "1110",
    'F' to "1111",
)