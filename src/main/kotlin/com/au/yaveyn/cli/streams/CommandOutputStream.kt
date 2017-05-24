package com.au.yaveyn.cli.streams

import java.io.BufferedWriter
import java.io.OutputStream
import java.io.OutputStreamWriter

/**
 * Interface for command output.
 */
interface CommandOutputStream {

    /**
     * Writes given string into a corresponding stream.
     */
    fun write(str: String): Unit

    /**
     * Writes given string and then '\n' into a corresponding stream.
     */
    fun writeln(str: String): Unit {
        write(str + "\n")
    }
}

class CommandOutputStreamImpl(stream: OutputStream) : CommandOutputStream {

    var writer = BufferedWriter(OutputStreamWriter(stream))

    override fun write(str: String) {
        writer.write(str)
    }

    fun flush() {
        writer.flush()
    }
}