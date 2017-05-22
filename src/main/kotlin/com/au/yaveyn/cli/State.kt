package com.au.yaveyn.cli

import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

/**
 * Environment.
 */
class State {
    private val variables: HashMap<String, String> = HashMap()
    private var exitRequested = false
    private var currentDirectory = Paths.get("").toAbsolutePath()


    private val systemRoot = findRoot()

    /**
     * Get current absolute path
     */
    fun getCurrentDirectory() = currentDirectory;

    /**
     * Get system root. "/" for Linux and "C:\" for Windows
     */
    fun getSystemRoot() = systemRoot

    /**
    * Set current absolute path
    */
    fun setCurrentDirectory(path : Path) {
        currentDirectory = path
    }

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

    fun findRoot() : Path {
        var root = Paths.get("")
        while (root.parent != null) {
            root = root.parent
        }
        return root;
    }

    /**
     * Checks whether shell instance is still running.
     */
    fun isRunning() = !exitRequested
}