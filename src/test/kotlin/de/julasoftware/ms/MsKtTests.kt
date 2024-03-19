package de.julasoftware.ms

import de.julasoftware.de.julasoftware.ms.parseMs
import de.julasoftware.de.julasoftware.ms.parseMsWithUnit
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class MsKtTests {

    @Test
    fun noException() {
        Assertions.assertDoesNotThrow { parseMs("1m") }
        Assertions.assertDoesNotThrow { parseMs("") }
        Assertions.assertDoesNotThrow { parseMs("53 milliseconds") }
        Assertions.assertDoesNotThrow { parseMsWithUnit("500") }
    }

    @Test
    fun successful_01() {
        assertEquals(parseMs("100"), 100.0)
        assertEquals(parseMs("1m"), 60000.0)
        assertEquals(parseMs("1h"), 3600000.0)
        assertEquals(parseMs("2d"), 172800000.0)
        assertEquals(parseMs("3w"), 1814400000.0)
        assertEquals(parseMs("1s"), 1000.0)
        assertEquals(parseMs("100ms"), 100.0)
        assertEquals(parseMs("1y"), 31557600000.0)
        assertEquals(parseMs("1.5h"), 5400000.0)
        assertEquals(parseMs("1   s"), 1000.0)
    }

    @Test
    fun fail_01() {
        assertNull(parseMs("☃"))
        assertNull(parseMs("10-.5"))
        assertNull(parseMs("ms"))
        assertNull(parseMs(""))
    }

    @Test
    fun successful_02() {
        assertEquals(parseMs("1.5H"), 5400000.0)
        assertEquals(parseMs(".5ms"), 0.5)
        assertEquals(parseMs("-100ms"), -100.0)
        assertEquals(parseMs("-1.5h"), -5400000.0)
        assertEquals(parseMs("-10.5h"), -37800000.0)
        assertEquals(parseMs("-.5h"), -1800000.0)
    }

    @Test
    fun successful_03() {
        assertEquals(parseMs("53 milliseconds"), 53.0)
        assertEquals(parseMs("17 msecs"), 17.0)
        assertEquals(parseMs("1 sec"), 1000.0)
        assertEquals(parseMs("1 min"), 60000.0)
        assertEquals(parseMs("1 hr"), 3600000.0)
        assertEquals(parseMs("2 days"), 172800000.0)
        assertEquals(parseMs("1 week"), 604800000.0)
        assertEquals(parseMs("1 year"), 31557600000.0)
        assertEquals(parseMs("1.5 hours"), 5400000.0)
        assertEquals(parseMs("-100 milliseconds"), -100.0)
        assertEquals(parseMs("-1.5 hours"), -5400000.0)
        assertEquals(parseMs("-.5 hr"), -1800000.0)
    }

    @Test
    fun successful_04() {
        assertEquals(parseMsWithUnit("500"), "500 ms")
        assertEquals(parseMsWithUnit("-500"), "-500 ms")

    }
}