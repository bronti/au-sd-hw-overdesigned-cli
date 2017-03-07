package com.au.yaveyn.cli.commands

import com.au.yaveyn.cli.State
import com.au.yaveyn.cli.streams.CommandInputStream
import com.au.yaveyn.cli.streams.CommandOutputStream

/**
 * Cat command implementation.
 */
class CatCommand(val filePath: String?) : Command() {

    override val name = "cat"

    override fun run(state: State, input: CommandInputStream?, output: CommandOutputStream) {
        output.write(getInput(filePath, input))
    }
}
