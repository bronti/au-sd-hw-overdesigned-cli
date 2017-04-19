package com.au.yaveyn.cli

import com.au.yaveyn.cli.commands.*
import com.au.yaveyn.cli.commands.CatCommand
import com.au.yaveyn.cli.exceptions.ShellUsageException

/**
 * Factory for creating commands.
 */
class CommandFactory {

    /**
     * Constructs a command.
     */
    fun constructCommand(command: String, params: List<String>): Command {

        fun checkParamsCount(paramsNeeded: Int?) {
            if (paramsNeeded == null) return

            if (params.size > paramsNeeded) {
                if (paramsNeeded == 0) throw ShellUsageException("zero parameters required in command '$command'")
                else throw ShellUsageException("at most $paramsNeeded parameters required in command ' $command'")
            }
        }

        return when (command) {
            "cat" -> {
                checkParamsCount(CatCommand.maxNumberOfParams)
                CatCommand(if (params.isEmpty()) null else params[0])
            }
            "echo" -> {
                checkParamsCount(EchoCommand.maxNumberOfParams)
                EchoCommand(params)
            }
            "exit" -> {
                checkParamsCount(ExitCommand.maxNumberOfParams)
                ExitCommand()
            }
            "pwd" -> {
                checkParamsCount(PwdCommand.maxNumberOfParams)
                PwdCommand()
            }
            "wc" -> {
                checkParamsCount(WcCommand.maxNumberOfParams)
                WcCommand(if (params.isEmpty()) null else params[0])
            }
            "cd" -> {
                checkParamsCount(CdCommand.maxNumberOfParams)
                CdCommand(if (params.isEmpty()) null else params[0])
            }
            "ls" -> {
                checkParamsCount(LsCommand.maxNumberOfParams)
                LsCommand()
            }
            else -> {
                UnknownCommand(command + if (!params.isEmpty()) " " + params.joinToString(" ") else "")
            }
        }

    }

}