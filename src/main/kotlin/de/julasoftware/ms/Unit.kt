package de.julasoftware.de.julasoftware.ms

private val millisecond = 1
private val second = millisecond * 1000
private val minute = second * 60
private val hour = minute * 60
private val day = hour * 24
private val week = day * 7
private val year = day * 365.25

enum class Unit(val factor: Number) {
    Years(year),
    Year(year),
    Yrs(year),
    Yr(year),
    Y(year),
    Weeks(week),
    Week(week),
    W(week),
    Days(day),
    Day(day),
    D(day),
    Hours(hour),
    Hour(hour),
    Hrs(hour),
    Hr(hour),
    H(hour),
    Minutes(minute),
    Minute(minute),
    Mins(minute),
    Min(minute),
    M(minute),
    Seconds(second),
    Second(second),
    Secs(second),
    Sec(second),
    s(second),
    Milliseconds(millisecond),
    Millisecond(millisecond),
    Msecs(millisecond),
    Msec(millisecond),
    Ms(millisecond);

    override fun toString(): String {
        return this.name.lowercase()
    }

    companion object {
        fun find(string: String): Unit? {
            return entries.find { it.toString() == string }
        }
    }
}