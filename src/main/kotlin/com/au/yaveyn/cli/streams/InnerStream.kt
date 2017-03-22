package com.au.yaveyn.cli.streams

/**
 * Stream for passing data between different commands in one pipe.
 */
class InnerStream : CommandOutputStream, CommandInputStream {
    val buff = StringBuilder()

    override fun write(str: String) {
        buff.append(str)
    }

    override fun toString(): String {
        return buff.toString()
    }
}