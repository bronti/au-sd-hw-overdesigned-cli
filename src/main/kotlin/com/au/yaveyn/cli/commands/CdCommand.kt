package com.au.yaveyn.cli.commands

import com.au.yaveyn.cli.State
import com.au.yaveyn.cli.streams.CommandInputStream
import com.au.yaveyn.cli.streams.CommandOutputStream
import java.nio.file.Paths

class CdCommand(val newPath: String?) : Command() {

    companion object {
        /**
         * Maximum number of parameters.
         */
        val maxNumberOfParams: Int? = 1
    }

    override val name = "cd"

    override fun run(state: State, input: CommandInputStream?, output: CommandOutputStream) {
        if (newPath != null) {
            state.setPath(getNewDir(state.getPath(), newPath))
        }
    }

    private fun getNewDir(oldPath: String, newPath: String) : String {
        return newPath
    }
}