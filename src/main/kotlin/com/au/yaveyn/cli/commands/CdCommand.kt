package com.au.yaveyn.cli.commands

import com.au.yaveyn.cli.State
import com.au.yaveyn.cli.exceptions.ShellUsageException
import com.au.yaveyn.cli.streams.CommandInputStream
import com.au.yaveyn.cli.streams.CommandOutputStream
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

/**
 *
 * Change directory command implementation
 *
 */

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
            val resPath = getNewDir(state, newPath)
            if (checkPathExistence(resPath)) {
                state.setCurrentDirectory(resPath)
            } else {
                throw ShellUsageException("path $resPath does not exist")
            }

        }
    }

    private fun getNewDir(state: State, newPath: String) : Path {

        if (isGoUpCommand(newPath)) {
            if (state.getCurrentDirectory() == state.getSystemRoot()) {
                return state.getCurrentDirectory()
            }
            return state.getCurrentDirectory().parent
        }

        if (isAbsoluteCommand(newPath)) {
            return Paths.get(newPath)
        }

        return concatenatePaths(state, newPath)
    }

    private fun concatenatePaths(state: State, path2 : String): Path {
        return state.getCurrentDirectory().resolve(path2)
    }

    private fun isGoUpCommand(newPath: String): Boolean {
        return newPath == ".."
    }

    private fun isAbsoluteCommand(newPath: String): Boolean {
        val path = Paths.get(newPath)
        return path.isAbsolute
    }

    private fun checkPathExistence(resPath: Path) : Boolean {
        val file = File(resPath.toString());
        return file.exists() && file.isDirectory
    }
}