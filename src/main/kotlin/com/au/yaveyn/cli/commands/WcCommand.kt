package com.au.yaveyn.cli.commands

import com.au.yaveyn.cli.State
import com.au.yaveyn.cli.streams.CommandInputStream
import com.au.yaveyn.cli.streams.CommandOutputStream
import com.sun.javaws.exceptions.InvalidArgumentException
import java.io.FileInputStream

/**
 * Wc command implementation.
 */
class WcCommand(val filePath: String?) : Command() {

    override val name = "wc"

    override fun run(state: State, input: CommandInputStream?, output: CommandOutputStream) {
        val inputString =
                when {
                    filePath != null -> FileInputStream(filePath).bufferedReader().readText()
                    input != null -> input.toString()
                    else -> throw InvalidArgumentException(arrayOf("wc: not enough parameters."))
                }

        if (inputString.isEmpty()) {
            output.writeln("0 0 0")
            return
        }

        var chars = 0
        var words = 0
        var lines = 0

        var inWord = false

        for (currentChar in inputString) {
            chars++

            if (inWord && currentChar.isWhitespace()) {
                words++
            }

            inWord = !currentChar.isWhitespace()

            if (currentChar == '\n') {
                lines++
            }
        }

        if (!inputString.last().isWhitespace()) words++
        lines++

        output.writeln("$lines $words $chars")
    }
}