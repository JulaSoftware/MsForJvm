package io.github.julasoftware.ms

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*

class MS {
    private val regex =
        "(?<value>-?(?:\\d+)?\\.?\\d+) *(?<type>milliseconds?|msecs?|ms|seconds?|secs?|s|minutes?|mins?|m|hours?|hrs?|h|days?|d|weeks?|w|years?|yrs?|y)?".toRegex(
            RegexOption.IGNORE_CASE
        )

    companion object {
        const val OPTION_LONG_FORMAT = 0x01
        const val OPTION_PRECISION_SINGLE_VALUE = 0x02
        const val OPTION_PRECISION_SPLIT_VALUE = 0x04
    }

    /**
     * Function to convert a human-readable time duration expression into a millisecond value
     * @param string human-readable value of a duration of time
     * @param numberLocale locale for parsing number decimals. Default is <code>English</code>
     * @return parsed value with unit
     */
    fun parse(string: String, numberLocale: Locale = Locale.ENGLISH): Double? {
        if (string.length > 100) {
            throw IllegalArgumentException("String must not be empty")
        }

        val match = regex.matchEntire(string)
        if (match != null) {
            val numberFormat = NumberFormat.getNumberInstance(numberLocale)
            val value = numberFormat.parse(match.groups["value"]?.value)
            val type = (match.groups["type"]?.value ?: "ms").lowercase()
            val unit = Unit.find(type) ?: throw Exception("Unsupported unit: $type")
            val res = value.toDouble().times(unit.factor)

            return res
        }

        return null
    }

    /**
     * Formats a duration of time in milliseconds into a human-readable expression
     * @param value time duration in milliseconds
     * @param flags Options for the output format
     * @return human-readable expression
     */
    fun format(value: Long, options: Options = Options()): String {
        return splitCheck(value, options)
    }

    /**
     * Formats a duration of time in milliseconds into a human-readable expression
     * @param value time duration in milliseconds
     * @param flags Options for the output format
     * @return human-readable expression
     */
    fun format(value: Int, options: Options = Options()): String {
        return splitCheck(value, options)
    }

    /**
     * Formats a duration of time in milliseconds into a human-readable expression
     * @param value time duration in milliseconds
     * @param flags Options for the output format
     * @return human-readable expression
     */
    fun format(value: BigInteger, options: Options = Options()): String {
        return splitCheck(value, options)
    }

    private fun splitCheck(value: Number, options: Options): String {
        if (options.splitTextOutput) {
            val list = mutableListOf<String>()
            var countDownMsNumber = value.toDouble()
            val tmpOptions = Options(longFormat = options.longFormat)

            // This may can be done more efficient
            do {
                val pair = calculateValue(countDownMsNumber, options)
                val numToDissect = BigDecimal(pair.first.toDouble())
                val remains = numToDissect.divideAndRemainder(BigDecimal(1))
                val partString = formatInternal(
                    remains[0].toLong(),
                    tmpOptions,
                    abs(remains[0] * BigDecimal(pair.second.factor)).toLong(),
                    Pair(remains[0].toLong(), pair.second)
                )
                list.add(partString)

                val partInMs = parse(partString) ?: break
                if (countDownMsNumber < 0)
                    countDownMsNumber = abs(countDownMsNumber) - abs(partInMs)
                else
                    countDownMsNumber -= partInMs

            } while (countDownMsNumber > 0 || abs(remains[1]) > BigDecimal.ZERO)

            return list.joinToString(options.splitDelimiter)
        }

        return formatInternal(value, options)
    }

    private fun formatInternal(
        value: Number, options: Options, absValue: Number? = null, pair: Pair<Number, Unit>? = null
    ): String {
        val absoluteValue = absValue ?: abs(value)
        val pairToProcess = pair ?: calculateValue(value, options)

        val space = if (options.longFormat) " " else ""
        val unitName = evalPair(pairToProcess, absoluteValue, options)

        return "${pairToProcess.first}${space}${unitName}"
    }

    private fun evalPair(pair: Pair<Number, Unit>, absoluteValue: Number, options: Options): String {
        return when (pair.second) {
            Unit.Years, Unit.Year, Unit.Yrs, Unit.Yr, Unit.Y -> if (options.longFormat) {
                plural(absoluteValue, Unit.Years.factor, "year")
            } else "y"

            Unit.Weeks, Unit.Week, Unit.W -> if (options.longFormat) {
                plural(absoluteValue, Unit.Weeks.factor, "week")
            } else "w"

            Unit.Days, Unit.Day, Unit.D -> if (options.longFormat) {
                plural(absoluteValue, Unit.Days.factor, "day")
            } else "d"

            Unit.Hours, Unit.Hour, Unit.Hrs, Unit.Hr, Unit.H -> if (options.longFormat) {
                plural(absoluteValue, Unit.Hours.factor, "hour")
            } else "h"


            Unit.Minutes, Unit.Minute, Unit.Mins, Unit.Min, Unit.M -> if (options.longFormat) {
                plural(absoluteValue, Unit.Minutes.factor, "minute")
            } else "m"


            Unit.Seconds, Unit.Second, Unit.Secs, Unit.Sec, Unit.S -> if (options.longFormat) {
                plural(absoluteValue, Unit.Seconds.factor, "second")
            } else "s"

            else -> "ms"
        }
    }

    private fun calculateValue(value: Number, options: Options): Pair<Number, Unit> {
        val absoluteValue = abs(value)

        return when {
            absoluteValue.toDouble() >= Unit.Years.factor && options.splitTextOutput -> Pair(
                precisionValue(div(value, Unit.Years.factor), options), Unit.Years
            )

            absoluteValue.toDouble() >= Unit.Weeks.factor && options.splitTextOutput -> Pair(
                precisionValue(div(value, Unit.Weeks.factor), options), Unit.Weeks
            )

            absoluteValue.toDouble() >= Unit.Days.factor -> Pair(
                precisionValue(div(value, Unit.Days.factor), options), Unit.Days
            )

            absoluteValue.toDouble() >= Unit.Hours.factor -> Pair(
                precisionValue(div(value, Unit.Hours.factor), options), Unit.Hours
            )

            absoluteValue.toDouble() >= Unit.Minutes.factor -> Pair(
                precisionValue(
                    div(value, Unit.Minutes.factor), options
                ), Unit.Minutes
            )

            absoluteValue.toDouble() >= Unit.Seconds.factor -> Pair(
                precisionValue(
                    div(value, Unit.Seconds.factor), options
                ), Unit.Seconds
            )

            else -> Pair(precisionValue(value.toDouble(), options), Unit.Milliseconds)
        }
    }

    private fun precisionValue(value: Double, options: Options): Number =
        if (!options.roundNumbers || options.splitTextOutput) {
            value
        } else if (options.roundPrecision > 0) {
            value.toBigDecimal().setScale(options.roundPrecision, RoundingMode.HALF_UP)
        } else {
            Math.round(value)
        }


    private fun plural(absValue: Number, factor: Double, unitName: String): String =
        if (absValue.toDouble() >= factor * 1.5) {
            "${unitName}s"
        } else {
            unitName
        }

    private fun <T : Number> abs(x: T): T {
        val absoluteValue: Number = when (x) {
            is Double -> kotlin.math.abs(x)
            is Int -> kotlin.math.abs(x)
            is Long -> kotlin.math.abs(x)
            is Float -> kotlin.math.abs(x)
            is BigDecimal -> x.abs()
            is BigInteger -> x.abs()
            else -> throw IllegalArgumentException("unsupported type ${x.javaClass}")
        }

        @Suppress("UNCHECKED_CAST") return absoluteValue as T
    }

    private fun <T : Number> div(value: T, otherValue: Double): Double {
        val divNum: Double = when (value) {
            is Double -> value.div(otherValue)
            is Int -> value.div(otherValue)
            is Long -> value.div(otherValue)
            is Float -> value.div(otherValue)
            is BigDecimal -> value.toDouble() / otherValue
            is BigInteger -> value.toDouble() / otherValue
            else -> throw IllegalArgumentException("unsupported type ${value.javaClass}")
        }

        return divNum
    }
}