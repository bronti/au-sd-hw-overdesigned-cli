package com.au.yaveyn.cli.commands

import com.au.yaveyn.cli.State
import com.au.yaveyn.cli.streams.CommandInputStream
import com.au.yaveyn.cli.streams.CommandOutputStream

/**
 * Exit command implementation.
 */
class ExitCommand : Command() {

    override val name = "exit"

    override fun run(state: State, input: CommandInputStream?, output: CommandOutputStream) = state.requestExit()
}
