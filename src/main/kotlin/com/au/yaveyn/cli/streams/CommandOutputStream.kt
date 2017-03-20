package com.au.yaveyn.cli.streams

/**
 * Interface for command output.
 */
interface CommandOutputStream {

    fun write(str: String): Unit

    fun writeln(str: String): Unit {
        write(str + "\n")
    }
}