package com.au.yaveyn.cli.commands

import com.au.yaveyn.cli.State
import com.au.yaveyn.cli.streams.CommandInputStream
import com.au.yaveyn.cli.streams.CommandOutputStream


/**
 * Interface for all runnable objects.
 */
interface ShellRunnable {

    /**
     * Runs object.
     */
    fun run(state: State, input: CommandInputStream?, output: CommandOutputStream)
}
