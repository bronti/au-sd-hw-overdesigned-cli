package com.au.yaveyn.cli

import com.au.yaveyn.cli.exceptions.ShellRuntimeException
import com.au.yaveyn.cli.exceptions.ShellUsageException
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
        while (state.isRunning()) {
            try {
                if (!commandReader.hasNext()) {
                    break
                }
                val (command, delimeter) = commandReader.getCommand()!!
                val runnable = parser.parse(command)
                runner.process(runnable, delimeter)
            } catch (e: ShellUsageException) {
                runner.showError(e.message!!)
            } catch (e: ShellRuntimeException) {
                runner.showError(e.message!!)
            }
        }
    }
}

/**
 * Entry point.
 */
fun main(args : Array<String>) {
    Shell(System.`in`, System.out).run()
}