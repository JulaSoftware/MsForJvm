package de.julasoftware.ms

import java.math.BigDecimal
import java.math.BigInteger
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

    fun format(value: Long, flags: Int = 0): String {
        return splitCheck(value, flags)
    }

    fun format(value: Int, flags: Int = 0): String {
        return splitCheck(value, flags)
    }

    fun format(value: BigInteger, flags: Int = 0): String {
        return splitCheck(value, flags)
    }

    private fun splitCheck(value: Number, flags: Int): String {
        if ((flags and OPTION_PRECISION_SPLIT_VALUE) != 0) {


            return formatInternal(value, flags)
        }

        return formatInternal(value, flags)
    }

    private fun formatInternal(value: Number, flags: Int): String {
        val absoluteValue = abs(value)
        val pair = calculateValue(value, flags)

        val longFormat = (flags and OPTION_LONG_FORMAT) != 0
        val space = if (longFormat) " " else ""

        val unitName = when (pair.second) {
            Unit.Years,
            Unit.Year,
            Unit.Yrs,
            Unit.Yr,
            Unit.Y -> if (longFormat) {
                plural(absoluteValue, Unit.Days.factor, "year")
            } else "y"

            Unit.Weeks,
            Unit.Week,
            Unit.W -> if (longFormat) {
                plural(absoluteValue, Unit.Days.factor, "week")
            } else "w"

            Unit.Days,
            Unit.Day,
            Unit.D -> if (longFormat) {
                plural(absoluteValue, Unit.Days.factor, "day")
            } else "d"

            Unit.Hours,
            Unit.Hour,
            Unit.Hrs,
            Unit.Hr,
            Unit.H -> if (longFormat) {
                plural(absoluteValue, Unit.Hours.factor, "hour")
            } else "h"


            Unit.Minutes,
            Unit.Minute,
            Unit.Mins,
            Unit.Min,
            Unit.M -> if (longFormat) {
                plural(absoluteValue, Unit.Minutes.factor, "minute")
            } else "m"


            Unit.Seconds,
            Unit.Second,
            Unit.Secs,
            Unit.Sec,
            Unit.S -> if (longFormat) {
                plural(absoluteValue, Unit.Seconds.factor, "second")
            } else "s"

            else -> "ms"
        }

        return "${pair.first}${space}${unitName}"
    }

    private fun calculateValue(value: Number, flags: Int): Pair<Number, Unit> {
        val absoluteValue = abs(value)
        val splitOptionActive = (flags and OPTION_PRECISION_SPLIT_VALUE) != 0

        return when {
            absoluteValue.toDouble() >= Unit.Years.factor && splitOptionActive -> Pair(
                precisionValue(div(value, Unit.Years.factor), flags),
                Unit.Years
            )

            absoluteValue.toDouble() >= Unit.Weeks.factor && splitOptionActive -> Pair(
                precisionValue(div(value, Unit.Weeks.factor), flags),
                Unit.Weeks
            )

            absoluteValue.toDouble() >= Unit.Days.factor -> Pair(
                precisionValue(div(value, Unit.Days.factor), flags),
                Unit.Days
            )

            absoluteValue.toDouble() >= Unit.Hours.factor -> Pair(
                precisionValue(div(value, Unit.Hours.factor), flags),
                Unit.Hours
            )

            absoluteValue.toDouble() >= Unit.Minutes.factor -> Pair(
                precisionValue(
                    div(value, Unit.Minutes.factor),
                    flags
                ), Unit.Minutes
            )

            absoluteValue.toDouble() >= Unit.Seconds.factor -> Pair(
                precisionValue(
                    div(value, Unit.Seconds.factor),
                    flags
                ), Unit.Seconds
            )

            else -> Pair(value, Unit.Milliseconds)
        }
    }

    private fun precisionValue(value: Double, flags: Int): Number {
        if ((flags and OPTION_PRECISION_SINGLE_VALUE) != 0 || (flags and OPTION_PRECISION_SPLIT_VALUE) != 0) {
            return value;
        }

        return Math.round(value)
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