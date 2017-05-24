package com.au.yaveyn.cli.streams

import java.io.Reader
import java.io.StringReader

/**
 * Stream for passing data between different commands in one pipe.
 */
class InnerStream : CommandOutputStream, CommandInputStream {
    val buff = StringBuilder()

    override fun write(str: String) {
        buff.append(str)
    }

    override fun toReader(): Reader {
        return StringReader(toString()).buffered()
    }

    override fun toString(): String {
        return buff.toString()
    }
}