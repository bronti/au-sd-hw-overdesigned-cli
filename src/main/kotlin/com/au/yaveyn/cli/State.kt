package com.au.yaveyn.cli

import java.util.*

/**
 * Environment.
 */
class State {
    private val variables: HashMap<String, String> = HashMap()
    private var exitRequested = false

    /**
     * Get a value of a variable.
     */
    fun getVar(name: String) = variables.getOrDefault(name, "")

    /**
     * Set a value for the variable.
     */
    fun setVar(name: String, value: String) = variables.set(name, value)

    /**
     * Request exit from a shell.
     */
    fun requestExit() {
        exitRequested = true
    }

    /**
     * Checks whether shell instance is still running.
     */
    fun isRunning() = !exitRequested
}