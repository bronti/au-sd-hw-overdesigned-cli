package com.au.yaveyn.cli.commands

import com.au.yaveyn.cli.CommandRunner
import com.au.yaveyn.cli.Delimeter
import com.au.yaveyn.cli.State
import org.junit.Assert
import org.junit.Test
import java.io.ByteArrayOutputStream

class CdLsCommandTest {
    @Test
    internal fun testSimpleLs() {
        val command = LsCommand()
        val testCommand = UnknownCommand("ls")
        val out = runCommand(command)
        val testOut = runCommand(testCommand)
        Assert.assertEquals(out.toString(), testOut.toString())
    }

    @Test
    internal fun testSimpleCdLs() {
        val state = State()
        runCommand(CdCommand("/"), state)

        val command = LsCommand()
        val testCommand = UnknownCommand("ls /")
        val out = runCommand(command, state)
        val testOut = runCommand(testCommand)
        Assert.assertEquals(out.toString(), testOut.toString())

    }

    private fun runCommand(command : Command, state: State = State()): ByteArrayOutputStream {
        val out = ByteArrayOutputStream()
        val runner = CommandRunner(state, out)
        runner.process(command, Delimeter.EOL)
        return out
    }
}