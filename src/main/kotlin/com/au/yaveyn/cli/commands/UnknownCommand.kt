package com.au.yaveyn.cli.commands

import com.au.yaveyn.cli.State
import com.au.yaveyn.cli.exceptions.ShellRuntimeException
import com.au.yaveyn.cli.exceptions.ShellUsageException
import com.au.yaveyn.cli.streams.CommandInputStream
import com.au.yaveyn.cli.streams.CommandOutputStream
import com.sun.javaws.exceptions.InvalidArgumentException
import java.io.IOException

/**
 * Unknown command implementation.
 */
class UnknownCommand(val command: String) : Command() {

    override val name = "unknown command"

    override fun run(state: State, input: CommandInputStream?, output: CommandOutputStream) {
        try {
            output.write(Runtime.getRuntime().exec(command).inputStream.bufferedReader().readText())
        } catch (e: Exception) {
            throw ShellRuntimeException("error while executing '$command'", e)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        if (!super.equals(other)) return false

        other as UnknownCommand

        if (command != other.command) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + command.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}