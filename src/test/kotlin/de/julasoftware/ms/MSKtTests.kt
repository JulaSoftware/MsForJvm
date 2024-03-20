package de.julasoftware.ms

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
        assertEquals(msInstance.parse("100"), 100.0)
        assertEquals(msInstance.parse("1m"), 60000.0)
        assertEquals(msInstance.parse("1h"), 3600000.0)
        assertEquals(msInstance.parse("2d"), 172800000.0)
        assertEquals(msInstance.parse("3w"), 1814400000.0)
        assertEquals(msInstance.parse("1s"), 1000.0)
        assertEquals(msInstance.parse("100ms"), 100.0)
        assertEquals(msInstance.parse("1y"), 31557600000.0)
        assertEquals(msInstance.parse("1.5h"), 5400000.0)
        assertEquals(msInstance.parse("1   s"), 1000.0)
    }

    @Test
    fun fail_to_ms_01() {
        assertNull(msInstance.parse("☃"))
        assertNull(msInstance.parse("10-.5"))
        assertNull(msInstance.parse("ms"))
        assertNull(msInstance.parse(""))
    }

    @Test
    fun successful_to_ms_02() {
        assertEquals(msInstance.parse("1.5H"), 5400000.0)
        assertEquals(msInstance.parse(".5ms"), 0.5)
        assertEquals(msInstance.parse("-100ms"), -100.0)
        assertEquals(msInstance.parse("-1.5h"), -5400000.0)
        assertEquals(msInstance.parse("-10.5h"), -37800000.0)
        assertEquals(msInstance.parse("-.5h"), -1800000.0)
    }

    @Test
    fun successful_to_ms_03() {
        assertEquals(msInstance.parse("53 milliseconds"), 53.0)
        assertEquals(msInstance.parse("17 msecs"), 17.0)
        assertEquals(msInstance.parse("1 sec"), 1000.0)
        assertEquals(msInstance.parse("1 min"), 60000.0)
        assertEquals(msInstance.parse("1 hr"), 3600000.0)
        assertEquals(msInstance.parse("2 days"), 172800000.0)
        assertEquals(msInstance.parse("1 week"), 604800000.0)
        assertEquals(msInstance.parse("1 year"), 31557600000.0)
        assertEquals(msInstance.parse("1.5 hours"), 5400000.0)
        assertEquals(msInstance.parse("-100 milliseconds"), -100.0)
        assertEquals(msInstance.parse("-1.5 hours"), -5400000.0)
        assertEquals(msInstance.parse("-.5 hr"), -1800000.0)
    }

    @Test
    fun successful_to_string_long_04() {
        assertEquals(msInstance.format(500, MS.OPTION_LONG_FORMAT), "500 ms")
        assertEquals(msInstance.format(-500, MS.OPTION_LONG_FORMAT), "-500 ms")

        assertEquals(msInstance.format(1000, MS.OPTION_LONG_FORMAT), "1 second")
        assertEquals(msInstance.format(1200, MS.OPTION_LONG_FORMAT), "1 second")
        assertEquals(msInstance.format(10000, MS.OPTION_LONG_FORMAT), "10 seconds")
        assertEquals(msInstance.format(-1000, MS.OPTION_LONG_FORMAT), "-1 second")
        assertEquals(msInstance.format(-1200, MS.OPTION_LONG_FORMAT), "-1 second")
        assertEquals(msInstance.format(-10000, MS.OPTION_LONG_FORMAT), "-10 seconds")

        assertEquals(msInstance.format(60 * 1000, MS.OPTION_LONG_FORMAT), "1 minute")
        assertEquals(msInstance.format(60 * 1200, MS.OPTION_LONG_FORMAT), "1 minute")
        assertEquals(msInstance.format(60 * 10000, MS.OPTION_LONG_FORMAT), "10 minutes")
        assertEquals(msInstance.format(-1 * 60 * 1000, MS.OPTION_LONG_FORMAT), "-1 minute")
        assertEquals(msInstance.format(-1 * 60 * 1200, MS.OPTION_LONG_FORMAT), "-1 minute")
        assertEquals(msInstance.format(-1 * 60 * 10000, MS.OPTION_LONG_FORMAT), "-10 minutes")

        assertEquals(msInstance.format(60 * 60 * 1000, MS.OPTION_LONG_FORMAT), "1 hour")
        assertEquals(msInstance.format(60 * 60 * 1200, MS.OPTION_LONG_FORMAT), "1 hour")
        assertEquals(msInstance.format(60 * 60 * 10000, MS.OPTION_LONG_FORMAT), "10 hours")
        assertEquals(msInstance.format(-1 * 60 * 60 * 1000, MS.OPTION_LONG_FORMAT), "-1 hour")
        assertEquals(msInstance.format(-1 * 60 * 60 * 1200, MS.OPTION_LONG_FORMAT), "-1 hour")
        assertEquals(msInstance.format(-1 * 60 * 60 * 10000, MS.OPTION_LONG_FORMAT), "-10 hours")

        assertEquals(msInstance.format(24 * 60 * 60 * 1000, MS.OPTION_LONG_FORMAT), "1 day")
        assertEquals(msInstance.format(24 * 60 * 60 * 1200, MS.OPTION_LONG_FORMAT), "1 day")
        assertEquals(msInstance.format(24 * 60 * 60 * 10000, MS.OPTION_LONG_FORMAT), "10 days")
        assertEquals(msInstance.format(-1 * 24 * 60 * 60 * 1000, MS.OPTION_LONG_FORMAT), "-1 day")
        assertEquals(msInstance.format(-1 * 24 * 60 * 60 * 1200, MS.OPTION_LONG_FORMAT), "-1 day")
        assertEquals(msInstance.format(-1 * 24 * 60 * 60 * 10000, MS.OPTION_LONG_FORMAT), "-10 days")

        assertEquals(msInstance.format(234234234, MS.OPTION_LONG_FORMAT), "3 days")
        assertEquals(msInstance.format(-234234234, MS.OPTION_LONG_FORMAT), "-3 days")
    }

    //@Test
    fun successful_to_string_w_precision_06() {
        assertEquals(msInstance.format(500, MS.OPTION_LONG_FORMAT + MS.OPTION_PRECISION_SINGLE_VALUE), "500 ms")
        assertEquals(msInstance.format(-500, MS.OPTION_LONG_FORMAT + MS.OPTION_PRECISION_SINGLE_VALUE), "-500 ms")

        assertEquals(msInstance.format(1000, MS.OPTION_LONG_FORMAT + MS.OPTION_PRECISION_SINGLE_VALUE), "1 second")
        assertEquals(msInstance.format(1200, MS.OPTION_LONG_FORMAT + MS.OPTION_PRECISION_SINGLE_VALUE), "1.2 second")
        assertEquals(msInstance.format(10000, MS.OPTION_LONG_FORMAT + MS.OPTION_PRECISION_SINGLE_VALUE), "10 seconds")
        assertEquals(msInstance.format(-1000, MS.OPTION_LONG_FORMAT + MS.OPTION_PRECISION_SINGLE_VALUE), "-1 second")
        assertEquals(
            msInstance.format(-1200, MS.OPTION_LONG_FORMAT + MS.OPTION_PRECISION_SINGLE_VALUE),
            "-1.2 second"
        )
        assertEquals(
            msInstance.format(-10000, MS.OPTION_LONG_FORMAT + MS.OPTION_PRECISION_SINGLE_VALUE), "-10 seconds"
        )

        assertEquals(
            msInstance.format(60 * 1000, MS.OPTION_LONG_FORMAT + MS.OPTION_PRECISION_SINGLE_VALUE), "1 minute"
        )
        assertEquals(
            msInstance.format(60 * 1200, MS.OPTION_LONG_FORMAT + MS.OPTION_PRECISION_SINGLE_VALUE), "1.2 minute"
        )
        assertEquals(
            msInstance.format(60 * 10000, MS.OPTION_LONG_FORMAT + MS.OPTION_PRECISION_SINGLE_VALUE), "10 minutes"
        )
        assertEquals(
            msInstance.format(-1 * 60 * 1000, MS.OPTION_LONG_FORMAT + MS.OPTION_PRECISION_SINGLE_VALUE), "-1 minute"
        )
        assertEquals(
            msInstance.format(-1 * 60 * 1200, MS.OPTION_LONG_FORMAT + MS.OPTION_PRECISION_SINGLE_VALUE), "-1.2 minute"
        )
        assertEquals(
            msInstance.format(-1 * 60 * 10000, MS.OPTION_LONG_FORMAT + MS.OPTION_PRECISION_SINGLE_VALUE),
            "-10 minutes"
        )

        assertEquals(
            msInstance.format(60 * 60 * 1000, MS.OPTION_LONG_FORMAT + MS.OPTION_PRECISION_SINGLE_VALUE), "1 hour"
        )
        assertEquals(
            msInstance.format(60 * 60 * 1200, MS.OPTION_LONG_FORMAT + MS.OPTION_PRECISION_SINGLE_VALUE), "1.2 hour"
        )
        assertEquals(
            msInstance.format(60 * 60 * 10000, MS.OPTION_LONG_FORMAT + MS.OPTION_PRECISION_SINGLE_VALUE), "10 hour"
        )
        assertEquals(
            msInstance.format(-1 * 60 * 60 * 1000, MS.OPTION_LONG_FORMAT + MS.OPTION_PRECISION_SINGLE_VALUE),
            "-1 hour"
        )
        assertEquals(
            msInstance.format(-1 * 60 * 60 * 1200, MS.OPTION_LONG_FORMAT + MS.OPTION_PRECISION_SINGLE_VALUE),
            "-1.2 hour"
        )
        assertEquals(
            msInstance.format(-1 * 60 * 60 * 10000, MS.OPTION_LONG_FORMAT + MS.OPTION_PRECISION_SINGLE_VALUE),
            "-10 hours"
        )

        assertEquals(
            msInstance.format(24 * 60 * 60 * 1000, MS.OPTION_LONG_FORMAT + MS.OPTION_PRECISION_SINGLE_VALUE), "1 day"
        )
        assertEquals(
            msInstance.format(24 * 60 * 60 * 1200, MS.OPTION_LONG_FORMAT + MS.OPTION_PRECISION_SINGLE_VALUE),
            "1.2 day"
        )
        assertEquals(
            msInstance.format(24 * 60 * 60 * 1000, MS.OPTION_LONG_FORMAT + MS.OPTION_PRECISION_SINGLE_VALUE),
            "10 days"
        )
        assertEquals(
            msInstance.format(-1 * 24 * 60 * 60 * 1000, MS.OPTION_LONG_FORMAT + MS.OPTION_PRECISION_SINGLE_VALUE),
            "-1 day"
        )
        assertEquals(
            msInstance.format(-1 * 24 * 60 * 60 * 1200, MS.OPTION_LONG_FORMAT + MS.OPTION_PRECISION_SINGLE_VALUE),
            "-1.2 day"
        )
        assertEquals(
            msInstance.format(-1 * 24 * 60 * 60 * 10000, MS.OPTION_LONG_FORMAT + MS.OPTION_PRECISION_SINGLE_VALUE),
            "-10 days"
        )

        assertEquals(msInstance.format(234234234, MS.OPTION_LONG_FORMAT + MS.OPTION_PRECISION_SINGLE_VALUE), "3 days")
        assertEquals(
            msInstance.format(-234234234, MS.OPTION_LONG_FORMAT + MS.OPTION_PRECISION_SINGLE_VALUE), "-3 days"
        )
    }

    @Test
    fun successful_to_string_short_05() {
        assertEquals(msInstance.format(500), "500ms")
        assertEquals(msInstance.format(-500), "-500ms")

        assertEquals(msInstance.format(1000), "1s")
        assertEquals(msInstance.format(1200), "1s")
        assertEquals(msInstance.format(10000), "10s")
        assertEquals(msInstance.format(-1000), "-1s")
        assertEquals(msInstance.format(-1200), "-1s")
        assertEquals(msInstance.format(-10000), "-10s")

        assertEquals(msInstance.format(60 * 1000), "1m")
        assertEquals(msInstance.format(60 * 1200), "1m")
        assertEquals(msInstance.format(60 * 10000), "10m")
        assertEquals(msInstance.format(-1 * 60 * 1000), "-1m")
        assertEquals(msInstance.format(-1 * 60 * 1200), "-1m")
        assertEquals(msInstance.format(-1 * 60 * 10000), "-10m")

        assertEquals(msInstance.format(60 * 60 * 1000), "1h")
        assertEquals(msInstance.format(60 * 60 * 1200), "1h")
        assertEquals(msInstance.format(60 * 60 * 10000), "10h")
        assertEquals(msInstance.format(-1 * 60 * 60 * 1000), "-1h")
        assertEquals(msInstance.format(-1 * 60 * 60 * 1200), "-1h")
        assertEquals(msInstance.format(-1 * 60 * 60 * 10000), "-10h")

        assertEquals(msInstance.format(24 * 60 * 60 * 1000), "1d")
        assertEquals(msInstance.format(24 * 60 * 60 * 1200), "1d")
        assertEquals(msInstance.format(24 * 60 * 60 * 10000), "10d")
        assertEquals(msInstance.format(-1 * 24 * 60 * 60 * 1000), "-1d")
        assertEquals(msInstance.format(-1 * 24 * 60 * 60 * 1200), "-1d")
        assertEquals(msInstance.format(-1 * 24 * 60 * 60 * 10000), "-10d")

        assertEquals(msInstance.format(234234234), "3d")
        assertEquals(msInstance.format(-234234234), "-3d")
    }
}