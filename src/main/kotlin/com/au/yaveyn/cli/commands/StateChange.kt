package com.au.yaveyn.cli.commands

import com.au.yaveyn.cli.State
import com.au.yaveyn.cli.streams.CommandInputStream
import com.au.yaveyn.cli.streams.CommandOutputStream

/**
 * Assign new value to a variable.
 */
class StateChange(val head: String, val tail: String) : ShellRunnable {
    override fun run(state: State, input: CommandInputStream?, output: CommandOutputStream) = state.setVar(head, tail)
}