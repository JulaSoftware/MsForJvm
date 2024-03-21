package io.github.julasoftware.ms

/**
 * Various options to control the conversion and output
 */
data class Options(
    /**
     * controls whether the text output is compact <code>20s</code> or verbose <code>20 seconds</code>
     *
     * Default: false
     */
    val longFormat: Boolean = false,

    /**
     * Option for rounding numbers or leave as is
     *
     * Default: true
     */
    val roundNumbers: Boolean = true,

    /**
     * When numbers are rounded, this decimal precision is applied
     *
     * Default: 0
     */
    val roundPrecision: Int = 0,

    /**
     * Option for splitting a larger time into its pieces.
     * e.g. instead an output of <code>10 days</code> it will return <code>1 week 3 days</code>
     *
     * Default: false
     */
    var splitTextOutput: Boolean = false,

    /**
     * When splitting is active it controls the delimiter of the segments in the string
     * e.g. instead of <code>1 week 3 days</code> it will generate <code>1 week, 3 days</code> when set to ", "
     *
     * Default: " " (whitespace)
     */
    var splitDelimiter: String = " "
)