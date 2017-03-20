package com.au.yaveyn.cli

import java.io.InputStream
import java.io.OutputStream

/**
 * Shell itself.
 */
class Shell(input: InputStream, output: OutputStream) {

    //todo: rename
    private val state = State()

    private val commandReader = CommandReader(input, Preprocessor(state))
    private val parser = Parser()
    private val runner = CommandRunner(state, output)

    /**
     * Runs shell instance.
     */
    fun run() {
        while (state.isRunning() && commandReader.hasNext()) {
            val (command, delimeter) = commandReader.getCommand()!!
            val runnable = parser.parse(command)
            runner.process(runnable, delimeter)
        }
    }
}

/**
 * Entry point.
 */
fun main(args : Array<String>) {
    Shell(System.`in`, System.out).run()
}