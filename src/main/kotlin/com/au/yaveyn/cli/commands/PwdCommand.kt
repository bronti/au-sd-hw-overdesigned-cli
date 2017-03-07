package com.au.yaveyn.cli.commands

import com.au.yaveyn.cli.State
import com.au.yaveyn.cli.streams.CommandInputStream
import com.au.yaveyn.cli.streams.CommandOutputStream
import java.nio.file.Paths

/**
 * Pwd command implementation.
 */
class PwdCommand() : Command() {

    override val name = "pwd"

    override fun run(state: State, input: CommandInputStream?, output: CommandOutputStream) {
        output.writeln(Paths.get("").toAbsolutePath().toString())
    }

}