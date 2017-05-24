package com.au.yaveyn.cli.streams

import java.io.Reader

/**
 * Interface for command input.
 */
interface CommandInputStream {

    /**
     * Returns stream's content as a string.
     */
    override fun toString(): String

    /**
     * Returns stream's content as a reader.
     */
    fun toReader(): Reader

}