package y2023

import Day
import util.FileReader
import y2023.Day05.formatAlmanac
import java.math.BigInteger

fun main() {
    formatAlmanac()
    Day05.run()
}

object Day05: Day() {
    private val data = FileReader.readAsStrings("y2023/Day05.txt")
    private val seeds = data[0].split(": ")[1].split(" ").map { BigInteger(it) }
    private val seedToSoil = mutableListOf<Almanac>()
    private val soilToFertilizer = mutableListOf<Almanac>()
    private val fertilizerToWater = mutableListOf<Almanac>()
    private val waterToLight = mutableListOf<Almanac>()
    private val lightToTemperature = mutableListOf<Almanac>()
    private val temperatureToHumidity = mutableListOf<Almanac>()
    private val humidityToLocation = mutableListOf<Almanac>()

    override fun part1(): BigInteger {
        val seedPaths = seeds.map { Seed(it) }
        for (seed in seedPaths) {
            seed.soil = writePath(seed.seed, seedToSoil)
            seed.fertilizer = writePath(seed.soil, soilToFertilizer)
            seed.water = writePath(seed.fertilizer, fertilizerToWater)
            seed.light = writePath(seed.water, waterToLight)
            seed.temperature = writePath(seed.light, lightToTemperature)
            seed.humidity = writePath(seed.temperature, temperatureToHumidity)
            seed.location = writePath(seed.humidity, humidityToLocation)
        }
        return seedPaths.minOf { it.location }
    }

    override fun part2(): Any {
        val seedsRanges = mutableListOf<BigIntegerRange>()
        for (i in 0..seeds.size-2 step 2) {
            seedsRanges.add(BigIntegerRange(seeds[i], seeds[i+1]))
        }
        var minLocation = BigInteger("-1")
        for (seedRange in seedsRanges) {
            var i = BigInteger(seedRange.start.toString())
            while (i <= (seedRange.start + seedRange.range)) {
                val seed = Seed(i)
                seed.soil = writePath(seed.seed, seedToSoil)
                seed.fertilizer = writePath(seed.soil, soilToFertilizer)
                seed.water = writePath(seed.fertilizer, fertilizerToWater)
                seed.light = writePath(seed.water, waterToLight)
                seed.temperature = writePath(seed.light, lightToTemperature)
                seed.humidity = writePath(seed.temperature, temperatureToHumidity)
                seed.location = writePath(seed.humidity, humidityToLocation)
                if (minLocation == BigInteger("-1") || seed.location < minLocation) {
                    minLocation = seed.location
                    println(minLocation)
                }
                i += BigInteger.ONE
            }
        }
        return minLocation
    }

    fun formatAlmanac() {
        var tmpAlmanac = mutableListOf<Almanac>()
        for (line in data) {
            if (line.isEmpty() && tmpAlmanac.isNotEmpty()) {
                if (seedToSoil.isEmpty()) seedToSoil += tmpAlmanac
                else if (soilToFertilizer.isEmpty()) soilToFertilizer += tmpAlmanac
                else if (fertilizerToWater.isEmpty()) fertilizerToWater += tmpAlmanac
                else if (waterToLight.isEmpty()) waterToLight += tmpAlmanac
                else if (lightToTemperature.isEmpty()) lightToTemperature += tmpAlmanac
                else if (temperatureToHumidity.isEmpty()) temperatureToHumidity += tmpAlmanac
                else if (humidityToLocation.isEmpty()) humidityToLocation += tmpAlmanac
                tmpAlmanac = mutableListOf()
            } else {
                if (line.isNotEmpty() && line.first().isDigit()) {
                    val (destination, source, range) = line.split(" ").map { BigInteger(it) }
                    tmpAlmanac.add(Almanac(source, destination, range))
                }
            }
        }
    }

    private fun writePath(value: BigInteger, almanac: MutableList<Almanac>): BigInteger {
        var returnValue = BigInteger(value.toString())
        for (line in almanac) {
            if (value in line.source..line.source + line.range) {
                returnValue = line.destination + (returnValue - line.source)
                break
            }
        }
        return returnValue
    }

}

class Almanac(val source: BigInteger, val destination: BigInteger, val range: BigInteger)

class Seed(val seed: BigInteger) {
    lateinit var soil: BigInteger
    lateinit var fertilizer: BigInteger
    lateinit var water: BigInteger
    lateinit var light: BigInteger
    lateinit var temperature: BigInteger
    lateinit var humidity: BigInteger
    lateinit var location: BigInteger
}

class BigIntegerRange(val start: BigInteger, val range: BigInteger)