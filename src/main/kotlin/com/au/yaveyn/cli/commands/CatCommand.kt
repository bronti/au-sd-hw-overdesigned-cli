package com.au.yaveyn.cli.commands

import com.au.yaveyn.cli.State
import com.au.yaveyn.cli.streams.CommandInputStream
import com.au.yaveyn.cli.streams.CommandOutputStream

/**
 * Cat command implementation.
 */
class CatCommand(val filePath: String?) : Command() {

    companion object {
        /**
        * Maximum number of parameters.
        */
        val maxNumberOfParams: Int? = 1
    }

    override val name = "cat"

    override fun run(state: State, input: CommandInputStream?, output: CommandOutputStream) {
        output.write(getInputReader(filePath, input).readText())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as CatCommand

        if (filePath != other.filePath) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = filePath?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        return result
    }
}
