package com.au.yaveyn.cli.commands

import com.au.yaveyn.cli.CommandRunner
import com.au.yaveyn.cli.Delimeter
import com.au.yaveyn.cli.State
import com.au.yaveyn.cli.streams.CommandInputStream
import com.au.yaveyn.cli.streams.CommandOutputStream
import java.io.ByteArrayOutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors

/*
*
* List files in current directory command implementation
*
* */
class LsCommand(val path: String?): Command() {

    companion object {
        /**
         * Maximum number of parameters.
         */
        val maxNumberOfParams: Int? = 1
    }

    override val name: String = "ls"

    override fun run(state: State, input: CommandInputStream?, output: CommandOutputStream) {
        if (path == null) {
            doLs(state, output)
        } else {
            val prevPath = state.getCurrentDirectory()
            CommandRunner(state, ByteArrayOutputStream()).process(CdCommand(path), Delimeter.EOL)
            doLs(state, output)
            state.setCurrentDirectory(prevPath)
        }
    }

    private fun doLs(state: State, output: CommandOutputStream) {
        val paths = Files.walk(state.getCurrentDirectory(), 1)
        val strings = paths
                .filter {path -> path != state.getCurrentDirectory()}
                .map { path -> toStringPath(path) }
                .filter { path -> !path.startsWith(".") && path != "" }
                .sorted()
                .collect(Collectors.toList())

        val merged = strings.joinToString(separator = "\n")
        output.write(merged)
    }

    private fun toStringPath(path: Path) : String {
        return path.fileName.toString()
    }
}