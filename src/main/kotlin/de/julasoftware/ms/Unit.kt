package de.julasoftware.ms

private const val millisecond = 1.0
private const val second = millisecond * 1000.0
private const val minute = second * 60.0
private const val hour = minute * 60.0
private const val day = hour * 24.0
private const val week = day * 7.0
private const val year = day * 365.25

enum class Unit(val factor: Double) {
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
    S(second),
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