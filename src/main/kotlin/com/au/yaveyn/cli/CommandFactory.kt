package com.au.yaveyn.cli

import com.au.yaveyn.cli.commands.*
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
                //todo: ex
                if (paramsNeeded == 0) throw InvalidArgumentException(arrayOf("Invalid usage of $command command: zero parameters required."))
                else throw InvalidArgumentException(arrayOf("Invalid usage of $command command: exactly $paramsNeeded parameter required"))
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