package com.au.yaveyn.cli.commands

import com.au.yaveyn.cli.State
import com.au.yaveyn.cli.streams.CommandInputStream
import com.au.yaveyn.cli.streams.CommandOutputStream
import java.nio.file.Paths

/**
 * Pwd command implementation.
 */
class PwdCommand : Command() {

    companion object {
        /**
         * Maximum number of parameters.
         */
        val maxNumberOfParams: Int? = 0
    }

    override val name = "pwd"

    override fun run(state: State, input: CommandInputStream?, output: CommandOutputStream) {
        output.write(Paths.get("").toAbsolutePath().toString())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as PwdCommand

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

}