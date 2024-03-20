package de.julasoftware.ms

import java.math.BigDecimal
import java.math.BigInteger
import java.text.NumberFormat
import java.util.*
import kotlin.math.roundToLong

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

    fun format(value: Number, flags: Int = 0): String {
        val absoluteValue = abs(value)

        val longFormat = (flags and OPTION_LONG_FORMAT) != 0
        val space = if (longFormat) " " else ""

        return when {
            absoluteValue.toDouble() > Unit.Days.factor -> "${Math.round(div(value, Unit.Days.factor).toDouble())} ${
                plural(
                    absoluteValue,
                    Unit.Days.factor,
                    " day"
                )
            }"

            absoluteValue.toDouble() > Unit.Hours.factor -> "${Math.round(div(value, Unit.Hours.factor).toDouble())} ${
                plural(
                    absoluteValue,
                    Unit.Hours.factor,
                    " hour"
                )
            }"

            absoluteValue.toDouble() > Unit.Minutes.factor -> "${
                Math.round(
                    div(
                        value,
                        Unit.Minutes.factor
                    ).toDouble()
                )
            } ${
                plural(
                    absoluteValue,
                    Unit.Minutes.factor,
                    " minute"
                )
            }"

            absoluteValue.toDouble() > Unit.Seconds.factor -> "${
                Math.round(
                    div(
                        value,
                        Unit.Seconds.factor
                    ).toDouble()
                )
            } ${
                plural(
                    absoluteValue,
                    Unit.Seconds.factor,
                    " second"
                )
            }"

            else -> "${value}${space}ms"
        }
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
            is Float -> kotlin.math.abs(x)
            is BigDecimal -> x.abs()
            is BigInteger -> x.abs()
            else -> throw IllegalArgumentException("unsupported type ${x.javaClass}")
        }

        @Suppress("UNCHECKED_CAST")
        return absoluteValue as T
    }

    private fun <T : Number> div(value: T, otherValue: Double): T {
        val divNum: Number = when (value) {
            is Double -> value.div(otherValue)
            is Int -> value.div(otherValue)
            is Float -> value.div(otherValue)
            is BigDecimal -> BigDecimal(value.toDouble() / otherValue)
            is BigInteger -> BigInteger((value.toDouble() / otherValue).roundToLong().toString())
            else -> throw IllegalArgumentException("unsupported type ${value.javaClass}")
        }

        @Suppress("UNCHECKED_CAST")
        return divNum as T
    }
}