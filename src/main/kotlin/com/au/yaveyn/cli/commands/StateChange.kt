package com.au.yaveyn.cli.commands

import com.au.yaveyn.cli.State
import com.au.yaveyn.cli.streams.CommandInputStream
import com.au.yaveyn.cli.streams.CommandOutputStream

/**
 * Assign new value to a variable.
 */
class StateChange(val head: String, val tail: String) : ShellRunnable {
    override fun run(state: State, input: CommandInputStream?, output: CommandOutputStream) = state.setVar(head, tail)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as StateChange

        if (head != other.head) return false
        if (tail != other.tail) return false

        return true
    }

    override fun hashCode(): Int {
        var result = head.hashCode()
        result = 31 * result + tail.hashCode()
        return result
    }
}