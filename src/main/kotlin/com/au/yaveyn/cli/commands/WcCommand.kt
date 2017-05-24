package com.au.yaveyn.cli.commands

import com.au.yaveyn.cli.State
import com.au.yaveyn.cli.streams.CommandInputStream
import com.au.yaveyn.cli.streams.CommandOutputStream

/**
 * Wc command implementation.
 */
class WcCommand(val filePath: String?) : Command() {

    companion object {
        /**
         * Maximum number of parameters.
         */
        val maxNumberOfParams: Int? = 1
    }

    override val name = "wc"

    override fun run(state: State, input: CommandInputStream?, output: CommandOutputStream) {
        val inputString = getInputReader(filePath, input).readText()

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
//        lines++

        output.write("$lines $words $chars")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as WcCommand

        if (filePath != other.filePath) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (filePath?.hashCode() ?: 0)
        result = 31 * result + name.hashCode()
        return result
    }
}