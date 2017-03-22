package com.au.yaveyn.cli.streams

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