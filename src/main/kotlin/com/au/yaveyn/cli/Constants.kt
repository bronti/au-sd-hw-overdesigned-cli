package com.au.yaveyn.cli

import com.sun.javaws.exceptions.InvalidArgumentException


/**
 * Checks whether or not a given char could be used in a variable name.
 */
fun charFitsIntoVar(c: Char) = c.isLetter() || c.isDigit() || c == '_'

/**
 * Types of command delimeters.
 */
enum class Delimeter(val delim: String) {
    PIPE("|"),
    EOL("\n")
}

/**
 * Gets a Delimeter instance for a given char.
 */
fun delimeterInstance(str: String): Delimeter {
    return when (str) {
        "|" -> Delimeter.PIPE
        "\n" -> Delimeter.EOL
        else -> throw InvalidArgumentException(arrayOf("Invalid delimeter"))
    }
}

/**
 * Pattern for a delimeter.
 */
val delimPattern: String = "([|\n])"

/**
 * Pattern for a command.
 */
val commandPattern: String = "([^|\n]+)"