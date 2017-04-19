package com.au.yaveyn.cli.commands

import com.au.yaveyn.cli.State
import com.au.yaveyn.cli.exceptions.ShellUsageException
import com.au.yaveyn.cli.streams.CommandInputStream
import com.au.yaveyn.cli.streams.CommandOutputStream
import java.io.File
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
            val resPath = getNewDir(state.getCurrentDirectory(), newPath)
            if (checkPathExistence(resPath)) {
                state.setCurrentDirectory(resPath)
            } else {
                throw ShellUsageException("path $resPath does not exist")
            }

        }
    }

    private fun getNewDir(oldPath: String, newPath: String) : String {

        if (isGoUpCommand(newPath)) {
            return cutLastFolder(oldPath)
        }

        if (isAbsoluteCommand(newPath)) {
            return newPath
        }

        return concatenatePaths(oldPath, newPath)
    }

    private fun concatenatePaths(path1 : String, path2 : String): String {
        if (path1 == "/") {
            return path1 + path2
        } else {
            return "$path1/$path2"
        }
    }

    private fun isGoUpCommand(newPath: String): Boolean {
        return newPath == ".."
    }

    private fun cutLastFolder(oldPath: String): String {
        if (oldPath == "/") {
            return oldPath
        }
        val pattern = "/[^/]*?\$"
        val regex = Regex(pattern)
        val res = regex.split(oldPath)
        return if (res[0] == "") "/" else res[0]
    }

    private fun isAbsoluteCommand(newPath: String): Boolean {
        return newPath.startsWith("/")
    }

    private fun checkPathExistence(resPath: String) : Boolean {
        val file = File(resPath);
        return file.exists() && file.isDirectory
    }
}