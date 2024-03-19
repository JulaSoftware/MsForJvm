package de.julasoftware.de.julasoftware.ms

import java.text.NumberFormat
import java.util.*
import java.util.regex.Pattern

private val pattern = Pattern.compile(
    "(?<value>-?(?:\\d+)?\\.?\\d+) *(?<type>milliseconds?|msecs?|ms|seconds?|secs?|s|minutes?|mins?|m|hours?|hrs?|h|days?|d|weeks?|w|years?|yrs?|y)?",
    Pattern.CASE_INSENSITIVE
)

fun parseMs(string: String): Double? {
    if (string.length > 100) {
        throw IllegalArgumentException("String must not be empty")
    }

    val match = pattern.matcher(string)
    if (match.matches()) {
        val numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH)
        val value = numberFormat.parse(match.group("value"))
        val type = (match.group("type") ?: "ms").lowercase()
        val unit = Unit.find(type) ?: throw Exception("Unsupported unit: $type")
        val res = value.toDouble().times(unit.factor.toDouble())

        return res
    }

    return null
}

fun parseMsWithUnit(string: String): String? {
    val value = parseMs(string)
    return value?.toString()
}