package com.au.yaveyn.cli.streams

/**
 * Interface for command input.
 */
interface CommandInputStream {

    /**
     * Returns stream's content as a string.
     */
    override fun toString(): String
}