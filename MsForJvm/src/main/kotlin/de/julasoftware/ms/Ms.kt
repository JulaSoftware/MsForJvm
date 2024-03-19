package de.julasoftware.de.julasoftware.ms

import java.text.NumberFormat
import java.util.*
import java.util.regex.Pattern

private val pattern = Pattern.compile(
    "(?<value>-?(?:\\d+)?\\.?\\d+) *(?<type>milliseconds?|msecs?|ms|seconds?|secs?|s|minutes?|mins?|m|hours?|hrs?|h|days?|d|weeks?|w|years?|yrs?|y)?",
    Pattern.CASE_INSENSITIVE
)

fun parse(str: String): Long? {
    if (str.isBlank() || str.length > 100) {
        return null;
    }

    val match = pattern.matcher(str)
    if (match.matches()) {
        val numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH)
        val value = numberFormat.parse(match.group("value"))
        val type = if (match.groupCount() > 1) match.group("type").lowercase() else "ms"
        val unit = Unit.find(type) ?: return null
        val res = value.toDouble().times(unit.factor.toDouble())

        return res.toLong()
    }

    return null
}