package com.au.yaveyn.cli.commands

import com.au.yaveyn.cli.State
import com.au.yaveyn.cli.exceptions.ShellRuntimeException
import com.au.yaveyn.cli.exceptions.ShellUsageException
import com.au.yaveyn.cli.streams.CommandInputStream
import com.au.yaveyn.cli.streams.CommandOutputStream
import java.io.FileInputStream
import java.io.FileNotFoundException

/**
 * Base class for all commands.
 */
abstract class Command : ShellRunnable {

    /**
     * Command name.
     */
    abstract val name: String

    /**
     * Runs command.
     */
    abstract override fun run(state: State, input: CommandInputStream?, output: CommandOutputStream)

    protected fun getInput(filePath: String?, input: CommandInputStream?): String {
        try {
            when {
                filePath != null -> return FileInputStream(filePath).bufferedReader().readText()
                input != null -> return input.toString()
                else -> throw ShellUsageException("$name: not enough parameters.")
            }
        } catch (ex: FileNotFoundException) {
            throw ShellRuntimeException("$name: can not read from file $filePath.", ex)
        }
    }
}