package com.au.yaveyn.cli.commands

import com.au.yaveyn.cli.State
import com.au.yaveyn.cli.streams.CommandInputStream
import com.au.yaveyn.cli.streams.CommandOutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Stream


class LsCommand: Command() {

    companion object {
        /**
         * Maximum number of parameters.
         */
        val maxNumberOfParams: Int? = 1
    }

    override val name: String = "ls"

    override fun run(state: State, input: CommandInputStream?, output: CommandOutputStream) {
        val paths = Files.walk(Paths.get(state.getPath()), 1)
        paths.forEach({path -> filteredPrint(path.toAbsolutePath().toString(), state.getPath(), output)})
    }

    private fun filteredPrint(path: String, absolute: String, output: CommandOutputStream) {
        val pattern = if(absolute == "/") "(?<=$absolute).*" else "(?<=$absolute/).*"
        val regex = Regex(pattern)
        val res = regex.find(path)
        if (res != null) {
            val out = res.groups[0]?.value
            if (out != null) {
                output.writeln(out)
            }
        }
    }
}