package io.github.julasoftware.ms

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class MSKtTests {

    private lateinit var msInstance: MS

    @BeforeEach
    fun setUp() {
        msInstance = MS()
    }

    @Test
    fun noException() {
        Assertions.assertDoesNotThrow { msInstance.parse("1m") }

        Assertions.assertDoesNotThrow { msInstance.parse("") }
        Assertions.assertNull(msInstance.parse(""))
        Assertions.assertNull(msInstance.parse("abc"))

        Assertions.assertDoesNotThrow { msInstance.parse("53 milliseconds") }
        Assertions.assertDoesNotThrow { msInstance.format(500) }
    }

    @Test
    fun successful_to_ms_01() {
        assertEquals(100.0, msInstance.parse("100"))
        assertEquals(60000.0, msInstance.parse("1m"))
        assertEquals(3600000.0, msInstance.parse("1h"))
        assertEquals(172800000.0, msInstance.parse("2d"))
        assertEquals(1814400000.0, msInstance.parse("3w"))
        assertEquals(1000.0, msInstance.parse("1s"))
        assertEquals(100.0, msInstance.parse("100ms"))
        assertEquals(31557600000.0, msInstance.parse("1y"))
        assertEquals(5400000.0, msInstance.parse("1.5h"))
        assertEquals(1000.0, msInstance.parse("1   s"))
    }

    @Test
    fun fail_to_ms_01() {
        assertNull(msInstance.parse("☃"))
        assertNull(msInstance.parse("ms"))
        assertNull(msInstance.parse(""))
    }

    @Test
    fun successful_to_ms_02() {
        assertEquals(5400000.0, msInstance.parse("1.5H"))
        assertEquals(0.5, msInstance.parse(".5ms"))
        assertEquals(-100.0, msInstance.parse("-100ms"))
        assertEquals(-5400000.0, msInstance.parse("-1.5h"))
        assertEquals(-37800000.0, msInstance.parse("-10.5h"))
        assertEquals(-1800000.0, msInstance.parse("-.5h"))
    }

    @Test
    fun successful_to_ms_03() {
        assertEquals(53.0, msInstance.parse("53 milliseconds"))
        assertEquals(17.0, msInstance.parse("17 msecs"))
        assertEquals(1000.0, msInstance.parse("1 sec"))
        assertEquals(60000.0, msInstance.parse("1 min"))
        assertEquals(3600000.0, msInstance.parse("1 hr"))
        assertEquals(172800000.0, msInstance.parse("2 days"))
        assertEquals(604800000.0, msInstance.parse("1 week"))
        assertEquals(31557600000.0, msInstance.parse("1 year"))
        assertEquals(5400000.0, msInstance.parse("1.5 hours"))
        assertEquals(-100.0, msInstance.parse("-100 milliseconds"))
        assertEquals(-5400000.0, msInstance.parse("-1.5 hours"))
        assertEquals(-1800000.0, msInstance.parse("-.5 hr"))
    }

    @Test
    fun successful_to_string_long_04() {
        assertEquals("500 ms", msInstance.format(500, Options(longFormat = true)))
        assertEquals("-500 ms", msInstance.format(-500, Options(longFormat = true)))

        assertEquals("1 second", msInstance.format(1000, Options(longFormat = true)))
        assertEquals("1 second", msInstance.format(1200, Options(longFormat = true)))
        assertEquals("10 seconds", msInstance.format(10000, Options(longFormat = true)))
        assertEquals("-1 second", msInstance.format(-1000, Options(longFormat = true)))
        assertEquals("-1 second", msInstance.format(-1200, Options(longFormat = true)))
        assertEquals("-10 seconds", msInstance.format(-10000, Options(longFormat = true)))

        assertEquals("1 minute", msInstance.format(60 * 1000, Options(longFormat = true)))
        assertEquals("1 minute", msInstance.format(60 * 1200, Options(longFormat = true)))
        assertEquals("10 minutes", msInstance.format(60 * 10000, Options(longFormat = true)))
        assertEquals("-1 minute", msInstance.format(-1 * 60 * 1000, Options(longFormat = true)))
        assertEquals("-1 minute", msInstance.format(-1 * 60 * 1200, Options(longFormat = true)))
        assertEquals("-10 minutes", msInstance.format(-1 * 60 * 10000, Options(longFormat = true)))

        assertEquals("1 hour", msInstance.format(60 * 60 * 1000, Options(longFormat = true)))
        assertEquals("1 hour", msInstance.format(60 * 60 * 1200, Options(longFormat = true)))
        assertEquals("10 hours", msInstance.format(60 * 60 * 10000, Options(longFormat = true)))
        assertEquals("-1 hour", msInstance.format(-1 * 60 * 60 * 1000, Options(longFormat = true)))
        assertEquals("-1 hour", msInstance.format(-1 * 60 * 60 * 1200, Options(longFormat = true)))
        assertEquals("-10 hours", msInstance.format(-1 * 60 * 60 * 10000, Options(longFormat = true)))

        assertEquals("1 day", msInstance.format(24 * 60 * 60 * 1000, Options(longFormat = true)))
        assertEquals("1 day", msInstance.format(24 * 60 * 60 * 1200, Options(longFormat = true)))
        assertEquals("10 days", msInstance.format(24 * 60 * 60 * 10000, Options(longFormat = true)))
        assertEquals("-1 day", msInstance.format(-1 * 24 * 60 * 60 * 1000, Options(longFormat = true)))
        assertEquals("-1 day", msInstance.format(-1 * 24 * 60 * 60 * 1200, Options(longFormat = true)))
        assertEquals("-10 days", msInstance.format(-1 * 24 * 60 * 60 * 10000, Options(longFormat = true)))

        assertEquals("3 days", msInstance.format(234234234, Options(longFormat = true)))
        assertEquals("-3 days", msInstance.format(-234234234, Options(longFormat = true)))
    }

    @Test
    fun successful_to_string_short_05() {
        assertEquals("500ms", msInstance.format(500))
        assertEquals("-500ms", msInstance.format(-500))

        assertEquals("1s", msInstance.format(1000))
        assertEquals("1s", msInstance.format(1200))
        assertEquals("10s", msInstance.format(10000))
        assertEquals("-1s", msInstance.format(-1000))
        assertEquals("-1s", msInstance.format(-1200))
        assertEquals("-10s", msInstance.format(-10000))

        assertEquals("1m", msInstance.format(60 * 1000))
        assertEquals("1m", msInstance.format(60 * 1200))
        assertEquals("10m", msInstance.format(60 * 10000))
        assertEquals("-1m", msInstance.format(-1 * 60 * 1000))
        assertEquals("-1m", msInstance.format(-1 * 60 * 1200))
        assertEquals("-10m", msInstance.format(-1 * 60 * 10000))

        assertEquals("1h", msInstance.format(60 * 60 * 1000))
        assertEquals("1h", msInstance.format(60 * 60 * 1200))
        assertEquals("10h", msInstance.format(60 * 60 * 10000))
        assertEquals("-1h", msInstance.format(-1 * 60 * 60 * 1000))
        assertEquals("-1h", msInstance.format(-1 * 60 * 60 * 1200))
        assertEquals("-10h", msInstance.format(-1 * 60 * 60 * 10000))

        assertEquals("1d", msInstance.format(24 * 60 * 60 * 1000))
        assertEquals("1d", msInstance.format(24 * 60 * 60 * 1200))
        assertEquals("10d", msInstance.format(24 * 60 * 60 * 10000))
        assertEquals("-1d", msInstance.format(-1 * 24 * 60 * 60 * 1000))
        assertEquals("-1d", msInstance.format(-1 * 24 * 60 * 60 * 1200))
        assertEquals("-10d", msInstance.format(-1 * 24 * 60 * 60 * 10000))

        assertEquals("3d", msInstance.format(234234234))
        assertEquals("-3d", msInstance.format(-234234234))
    }

    @Test
    fun successful_to_string_w_precision_06() {
        assertEquals("500.0 ms", msInstance.format(500, Options(longFormat = true, roundNumbers = false)))
        assertEquals("-500.0 ms", msInstance.format(-500, Options(longFormat = true, roundNumbers = false)))

        assertEquals("1.0 second", msInstance.format(1000, Options(longFormat = true, roundNumbers = false)))
        assertEquals("1.2 second", msInstance.format(1200, Options(longFormat = true, roundNumbers = false)))
        assertEquals("10.0 seconds", msInstance.format(10000, Options(longFormat = true, roundNumbers = false)))
        assertEquals("-1.0 second", msInstance.format(-1000, Options(longFormat = true, roundNumbers = false)))
        assertEquals("-1.2 second", msInstance.format(-1200, Options(longFormat = true, roundNumbers = false)))
        assertEquals("-10.0 seconds", msInstance.format(-10000, Options(longFormat = true, roundNumbers = false)))

        assertEquals("1.0 minute", msInstance.format(60 * 1000, Options(longFormat = true, roundNumbers = false)))
        assertEquals("1.2 minute", msInstance.format(60 * 1200, Options(longFormat = true, roundNumbers = false)))
        assertEquals("10.0 minutes", msInstance.format(60 * 10000, Options(longFormat = true, roundNumbers = false)))
        assertEquals("-1.0 minute", msInstance.format(-1 * 60 * 1000, Options(longFormat = true, roundNumbers = false)))
        assertEquals(
            "-1.2 minute",
            msInstance.format(-1 * 60 * 1200, Options(longFormat = true, roundNumbers = false)),
        )
        assertEquals(
            "-10.0 minutes",
            msInstance.format(-1 * 60 * 10000, Options(longFormat = true, roundNumbers = false)),
        )

        assertEquals("1.0 hour", msInstance.format(60 * 60 * 1000, Options(longFormat = true, roundNumbers = false)))
        assertEquals("1.2 hour", msInstance.format(60 * 60 * 1200, Options(longFormat = true, roundNumbers = false)))
        assertEquals("10.0 hours", msInstance.format(60 * 60 * 10000, Options(longFormat = true, roundNumbers = false)))
        assertEquals(
            "-1.0 hour",
            msInstance.format(-1 * 60 * 60 * 1000, Options(longFormat = true, roundNumbers = false)),
        )
        assertEquals(
            "-1.2 hour",
            msInstance.format(-1 * 60 * 60 * 1200, Options(longFormat = true, roundNumbers = false)),
        )

        assertEquals(
            "-10.0 hours",
            msInstance.format(-1 * 60 * 60 * 10000, Options(longFormat = true, roundNumbers = false)),
        )

        assertEquals(
            "1.0 day",
            msInstance.format(24 * 60 * 60 * 1000, Options(longFormat = true, roundNumbers = false))
        )
        assertEquals(
            "1.2 day",
            msInstance.format(24 * 60 * 60 * 1200, Options(longFormat = true, roundNumbers = false)),
        )
        assertEquals(
            "10.0 days",
            msInstance.format(24 * 60 * 60 * 10000, Options(longFormat = true, roundNumbers = false)),
        )
        assertEquals(
            "-1.0 day",
            msInstance.format(-1 * 24 * 60 * 60 * 1000, Options(longFormat = true, roundNumbers = false)),
        )
        assertEquals(
            "-1.2 day",
            msInstance.format(-1 * 24 * 60 * 60 * 1200, Options(longFormat = true, roundNumbers = false)),
        )
        assertEquals(
            "-10.0 days",
            msInstance.format(-1 * 24 * 60 * 60 * 10000, Options(longFormat = true, roundNumbers = false)),
        )

        assertEquals("2.711044375 days", msInstance.format(234234234, Options(longFormat = true, roundNumbers = false)))
        assertEquals(
            "-2.711044375 days",
            msInstance.format(-234234234, Options(longFormat = true, roundNumbers = false))
        )

        assertEquals("2.71 days", msInstance.format(234234234, Options(roundPrecision = 2, longFormat = true)))
        assertEquals("-2.71 days", msInstance.format(-234234234, Options(roundPrecision = 2, longFormat = true)))
    }

    @Test
    fun successful_to_string_w_precision_split_07() {
        assertEquals("500ms", msInstance.format(500, Options(splitTextOutput = true)))
        assertEquals("-500ms", msInstance.format(-500, Options(splitTextOutput = true)))

        assertEquals("1s", msInstance.format(1000, Options(splitTextOutput = true)))
        assertEquals("1s 200ms", msInstance.format(1200, Options(splitTextOutput = true)))
        assertEquals("10s", msInstance.format(10000, Options(splitTextOutput = true)))
        assertEquals("-1s", msInstance.format(-1000, Options(splitTextOutput = true)))
        assertEquals("-1s 200ms", msInstance.format(-1200, Options(splitTextOutput = true)))
        assertEquals("-10s", msInstance.format(-10000, Options(splitTextOutput = true)))

        assertEquals("1m", msInstance.format(60 * 1000, Options(splitTextOutput = true)))
        assertEquals("1m 12s", msInstance.format(60 * 1200, Options(splitTextOutput = true)))
        assertEquals("10m", msInstance.format(60 * 10000, Options(splitTextOutput = true)))
        assertEquals("-1m", msInstance.format(-1 * 60 * 1000, Options(splitTextOutput = true)))
        assertEquals("-1m 12s", msInstance.format(-1 * 60 * 1200, Options(splitTextOutput = true)))
        assertEquals("-10m", msInstance.format(-1 * 60 * 10000, Options(splitTextOutput = true)))

        assertEquals("1h", msInstance.format(60 * 60 * 1000, Options(splitTextOutput = true)))
        assertEquals("1h 12m", msInstance.format(60 * 60 * 1200, Options(splitTextOutput = true)))
        assertEquals("10h", msInstance.format(60 * 60 * 10000, Options(splitTextOutput = true)))
        assertEquals("-1h", msInstance.format(-1 * 60 * 60 * 1000, Options(splitTextOutput = true)))
        assertEquals("-1h 12m", msInstance.format(-1 * 60 * 60 * 1200, Options(splitTextOutput = true)))
        assertEquals("-10h", msInstance.format(-1 * 60 * 60 * 10000, Options(splitTextOutput = true)))

        assertEquals("1d", msInstance.format(24 * 60 * 60 * 1000, Options(splitTextOutput = true)))
        assertEquals("1d 4h 48m", msInstance.format(24 * 60 * 60 * 1200, Options(splitTextOutput = true)))
        assertEquals("1w 3d", msInstance.format(24 * 60 * 60 * 10000, Options(splitTextOutput = true)))
        assertEquals("-1d", msInstance.format(-1 * 24 * 60 * 60 * 1000, Options(splitTextOutput = true)))
        assertEquals("-1d 4h 48m", msInstance.format(-1 * 24 * 60 * 60 * 1200, Options(splitTextOutput = true)))
        assertEquals("-1w 3d", msInstance.format(-1 * 24 * 60 * 60 * 10000, Options(splitTextOutput = true)))

        assertEquals("2.71d", msInstance.format(234234234, Options(roundPrecision = 2)))
        assertEquals("-2.71d", msInstance.format(-234234234, Options(roundPrecision = 2)))
    }

    @Test
    fun successful_to_string_w_precision_split_long_08() {
        assertEquals("500 ms", msInstance.format(500, Options(splitTextOutput = true, longFormat = true)))
        assertEquals("-500 ms", msInstance.format(-500, Options(splitTextOutput = true, longFormat = true)))

        assertEquals("1 second", msInstance.format(1000, Options(splitTextOutput = true, longFormat = true)))
        assertEquals(
            "1 second, 200 ms",
            msInstance.format(1200, Options(splitTextOutput = true, longFormat = true, splitDelimiter = ", "))
        )
        assertEquals(
            "10 seconds",
            msInstance.format(10000, Options(splitTextOutput = true, longFormat = true, splitDelimiter = ", "))
        )
        assertEquals(
            "-1 second",
            msInstance.format(-1000, Options(splitTextOutput = true, longFormat = true, splitDelimiter = ", "))
        )
        assertEquals(
            "-1 second, 200 ms",
            msInstance.format(-1200, Options(splitTextOutput = true, longFormat = true, splitDelimiter = ", "))
        )
        assertEquals(
            "-10 seconds",
            msInstance.format(-10000, Options(splitTextOutput = true, longFormat = true, splitDelimiter = ", "))
        )

        assertEquals(
            "1 minute",
            msInstance.format(60 * 1000, Options(splitTextOutput = true, longFormat = true, splitDelimiter = ", "))
        )
        assertEquals(
            "1 minute, 12 seconds",
            msInstance.format(60 * 1200, Options(splitTextOutput = true, longFormat = true, splitDelimiter = ", "))
        )
        assertEquals(
            "10 minutes",
            msInstance.format(
                60 * 10000,
                Options(splitTextOutput = true, longFormat = true, splitDelimiter = ", ")
            )
        )
        assertEquals(
            "-1 minute",
            msInstance.format(
                -1 * 60 * 1000,
                Options(splitTextOutput = true, longFormat = true, splitDelimiter = ", ")
            )
        )
        assertEquals(
            "-1 minute, 12 seconds",
            msInstance.format(
                -1 * 60 * 1200,
                Options(splitTextOutput = true, longFormat = true, splitDelimiter = ", ")
            )
        )
        assertEquals(
            "-10 minutes",
            msInstance.format(
                -1 * 60 * 10000,
                Options(splitTextOutput = true, longFormat = true, splitDelimiter = ", ")
            )
        )

        assertEquals(
            "1 hour",
            msInstance.format(
                60 * 60 * 1000,
                Options(splitTextOutput = true, longFormat = true, splitDelimiter = ", ")
            )
        )
        assertEquals(
            "1 hour, 12 minutes",
            msInstance.format(
                60 * 60 * 1200,
                Options(splitTextOutput = true, longFormat = true, splitDelimiter = ", ")
            )
        )
        assertEquals(
            "10 hours",
            msInstance.format(
                60 * 60 * 10000,
                Options(splitTextOutput = true, longFormat = true, splitDelimiter = ", ")
            )
        )
        assertEquals(
            "-1 hour",
            msInstance.format(
                -1 * 60 * 60 * 1000,
                Options(splitTextOutput = true, longFormat = true, splitDelimiter = ", ")
            )
        )
        assertEquals(
            "-1 hour, 12 minutes",
            msInstance.format(
                -1 * 60 * 60 * 1200,
                Options(splitTextOutput = true, longFormat = true, splitDelimiter = ", ")
            )
        )
        assertEquals(
            "-10 hours",
            msInstance.format(
                -1 * 60 * 60 * 10000,
                Options(splitTextOutput = true, longFormat = true, splitDelimiter = ", ")
            )
        )

        assertEquals(
            "1 day",
            msInstance.format(
                24 * 60 * 60 * 1000,
                Options(splitTextOutput = true, longFormat = true, splitDelimiter = ", ")
            )
        )
        assertEquals(
            "1 day, 4 hours, 48 minutes",
            msInstance.format(
                24 * 60 * 60 * 1200,
                Options(splitTextOutput = true, longFormat = true, splitDelimiter = ", ")
            )
        )
        assertEquals(
            "1 week, 3 days",
            msInstance.format(
                24 * 60 * 60 * 10000,
                Options(splitTextOutput = true, longFormat = true, splitDelimiter = ", ")
            )
        )
        assertEquals(
            "-1 day",
            msInstance.format(
                -1 * 24 * 60 * 60 * 1000,
                Options(splitTextOutput = true, longFormat = true, splitDelimiter = ", ")
            )
        )
        assertEquals(
            "-1 day, 4 hours, 48 minutes",
            msInstance.format(
                -1 * 24 * 60 * 60 * 1200,
                Options(splitTextOutput = true, longFormat = true, splitDelimiter = ", ")
            )
        )
        assertEquals(
            "-1 week, 3 days",
            msInstance.format(
                -1 * 24 * 60 * 60 * 10000,
                Options(splitTextOutput = true, longFormat = true, splitDelimiter = ", ")
            )
        )
    }

    @Test
    fun successful_parse_split_multi_values_09() {
        assertEquals(500.0, msInstance.parse("500 ms"))
        assertEquals(-500.0, msInstance.parse("-500 ms"))

        assertEquals(1000.0, msInstance.parse("1 second"))
        assertEquals(1200.0, msInstance.parse("1 second, 200 ms"))
        assertEquals(1200.0, msInstance.parse("1 second, -200 ms"))
        assertEquals(10000.0, msInstance.parse("10 seconds"))
        assertEquals(-1000.0, msInstance.parse("-1 second"))
        assertEquals(-1200.0, msInstance.parse("-1 second, 200 ms"))
        assertEquals(-10000.0, msInstance.parse("-10 seconds"))
        assertEquals(60 * 1000.0, msInstance.parse("1 minute"))
        assertEquals(60 * 1200.0, msInstance.parse("1 minute, 12 seconds"))
        assertEquals(60 * 10000.0, msInstance.parse("10 minutes"))
        assertEquals(-1 * 60 * 1000.0, msInstance.parse("-1 minute"))
        assertEquals(-1 * 60 * 1200.0, msInstance.parse("-1 minute, 12 seconds"))
        assertEquals(-1 * 60 * 10000.0, msInstance.parse("-10 minutes"))

        assertEquals(60 * 60 * 1000.0, msInstance.parse("1 hour"))
        assertEquals(60 * 60 * 1200.0, msInstance.parse("1 hour, 12 minutes"))
        assertEquals(60 * 60 * 10000.0, msInstance.parse("10 hours"))
        assertEquals(-1 * 60 * 60 * 1000.0, msInstance.parse("-1 hour"))
        assertEquals(-1 * 60 * 60 * 1200.0, msInstance.parse("-1 hour, 12 minutes"))
        assertEquals(-1 * 60 * 60 * 10000.0, msInstance.parse("-10 hours"))

        assertEquals(24 * 60 * 60 * 1000.0, msInstance.parse("1 day"))
        assertEquals(24 * 60 * 60 * 1200.0, msInstance.parse("1 day, 4 hours, 48 minutes"))
        assertEquals(24 * 60 * 60 * 10000.0, msInstance.parse("1 week, 3 days"))
        assertEquals(-1 * 24 * 60 * 60 * 1000.0, msInstance.parse("-1 day"))
        assertEquals(-1 * 24 * 60 * 60 * 1200.0, msInstance.parse("-1 day, 4 hours, 48 minutes"))
        assertEquals(-1 * 24 * 60 * 60 * 10000.0, msInstance.parse("-1 week, 3 days"))
    }
}