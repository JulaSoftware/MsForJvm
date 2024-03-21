package io.github.julasoftware.ms

data class Options(
    val longFormat: Boolean = false,
    val roundNumbers: Boolean = true,
    val roundPrecision: Int = 0,
    var splitTextOutput: Boolean = false,
    var splitTextOutputChar: String = " "
)