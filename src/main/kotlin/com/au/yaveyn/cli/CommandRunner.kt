package com.au.yaveyn.cli


import com.au.yaveyn.cli.commands.ShellRunnable
import com.au.yaveyn.cli.streams.CommandInputStream
import com.au.yaveyn.cli.streams.CommandOutputStream
import com.au.yaveyn.cli.streams.InnerStream
import java.io.BufferedWriter
import java.io.OutputStream
import java.io.OutputStreamWriter

/**
 * Chooses appropriate input and output streams depending on pipes and runs a command.
 */
class CommandRunner(val state: State, output: OutputStream) {

    private class OutputStreamWrapper(stream: OutputStream) : CommandOutputStream {

        var writer = BufferedWriter(OutputStreamWriter(stream))

        override fun write(str: String) {
            writer.write(str)
        }

        fun flush() {
            writer.flush()
        }
    }

    private val outputStream = OutputStreamWrapper(output)
    private var currentInputStream: CommandInputStream? = null

    /**
     * Runs a command.
     */
    fun process(command: ShellRunnable, delim: Delimeter) {
        if (delim == Delimeter.EOL) {
            command.run(state, currentInputStream, outputStream)
            outputStream.flush()
            currentInputStream = null
        } else {
            val innerOutput = InnerStream()
            command.run(state, currentInputStream, innerOutput)
            currentInputStream = innerOutput
        }
    }

    /**
     * Shows error message.
     */
    fun showError(msg: String) {
        outputStream.writeln(msg)
        outputStream.flush()
    }
}