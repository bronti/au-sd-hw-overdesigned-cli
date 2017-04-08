package com.au.yaveyn.cli.commands

import com.au.yaveyn.cli.State
import com.au.yaveyn.cli.streams.CommandInputStream
import com.au.yaveyn.cli.streams.CommandOutputStream

/**
 * Echo command implementation.
 */
class EchoCommand(val params: List<String>) : Command() {

    companion object {
        /**
         * Maximum number of parameters.
         */
        val maxNumberOfParams: Int? = null
    }

    override val name = "echo"

    override fun run(state: State, input: CommandInputStream?, output: CommandOutputStream) {
        output.write(params.joinToString(" "))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        if (!super.equals(other)) return false

        other as EchoCommand

        if (params != other.params) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + params.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}
