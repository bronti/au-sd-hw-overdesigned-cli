package com.au.yaveyn.cli.commands

import com.au.yaveyn.cli.State
import com.au.yaveyn.cli.streams.CommandInputStream
import com.au.yaveyn.cli.streams.CommandOutputStream

/**
 * Echo command implementation.
 */
class EchoCommand(val params: List<String>) : Command() {

    override val name = "echo"

    override fun run(state: State, input: CommandInputStream?, output: CommandOutputStream) {
        output.writeln(params.joinToString(" "))
    }
}
