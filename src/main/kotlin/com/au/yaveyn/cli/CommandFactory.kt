package com.au.yaveyn.cli

import com.au.yaveyn.cli.commands.*
import com.au.yaveyn.cli.exceptions.ShellUsageException
import com.sun.javaws.exceptions.InvalidArgumentException

/**
 * Factory for creating commands.
 */
class CommandFactory {

    /**
     * Constructs a command.
     */
    fun constructCommand(command: String, params: List<String>): Command {

        fun checkParamsCount(paramsNeeded: Int) {
            if (params.size > paramsNeeded) {
                if (paramsNeeded == 0) throw ShellUsageException("zero parameters required in command '$command'")
                else throw ShellUsageException("exactly $paramsNeeded parameters required in command ' $command'")
            }
        }

        return when (command) {
            "cat" -> {
                checkParamsCount(1)
                CatCommand(if (params.isEmpty()) null else params[0])
            }
            "echo" -> {
                EchoCommand(params)
            }
            "exit" -> {
                checkParamsCount(0)
                ExitCommand()
            }
            "pwd" -> {
                checkParamsCount(0)
                PwdCommand()
            }
            "wc" -> {
                checkParamsCount(1)
                WcCommand(if (params.isEmpty()) null else params[0])
            }
            else -> {
                UnknownCommand(command + " " + params.joinToString(" "))
            }
        }

    }

}