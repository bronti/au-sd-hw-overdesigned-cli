package com.au.yaveyn.cli.commands

import com.au.yaveyn.cli.State
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
        } catch (e: IOException) {
            //todo: ex
            throw InvalidArgumentException(arrayOf("Invalid command '$command'."))
        }
    }
}