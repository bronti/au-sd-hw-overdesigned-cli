package com.au.yaveyn.cli

import com.au.yaveyn.cli.commands.EchoCommand
import com.au.yaveyn.cli.commands.WcCommand
import org.junit.Assert
import org.junit.Test
import java.io.ByteArrayOutputStream

class CommandRunnerTest {

    @Test
    fun testEol() {
        val command = EchoCommand(listOf("ololo"))
        val out = ByteArrayOutputStream()
        val runner = CommandRunner(State(), out)
        runner.process(command, Delimeter.EOL)
        Assert.assertEquals("ololo\n", out.toString())
    }

    @Test
    fun testPipe() {
        val command1 = EchoCommand(listOf("ololo"))
        val command2 = EchoCommand(listOf("oxxxy rulezz"))
        val out = ByteArrayOutputStream()
        val runner = CommandRunner(State(), out)
        runner.process(command1, Delimeter.PIPE)
        runner.process(command2, Delimeter.EOL)

        Assert.assertEquals("oxxxy rulezz\n", out.toString())
    }

    @Test
    fun testPipeWithStreams() {
        val command1 = EchoCommand(listOf("1"))
        val command2 = WcCommand(null)
        val out = ByteArrayOutputStream()
        val runner = CommandRunner(State(), out)
        runner.process(command1, Delimeter.PIPE)
        runner.process(command2, Delimeter.EOL)

        Assert.assertEquals("1 1 1\n", out.toString())
    }

    @Test
    fun testErrorMsg() {
        val out = ByteArrayOutputStream()
        val runner = CommandRunner(State(), out)
        runner.showError("This is so wrong!")

        Assert.assertEquals("This is so wrong!\n", out.toString())
    }
}