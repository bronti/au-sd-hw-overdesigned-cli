package com.au.yaveyn.cli.commands

import com.au.yaveyn.cli.*
import com.au.yaveyn.cli.exceptions.ShellRuntimeException
import com.au.yaveyn.cli.exceptions.ShellUsageException
import com.natpryce.hamkrest.Matcher
import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.throws
import org.junit.Assert
import org.junit.Test
import java.io.ByteArrayOutputStream

/**
 * Created by bronti on 08.04.17.
 */

class WcCommandTest {

    @Test
    internal fun testEmptyLastLine() {
        val command = WcCommand("testData/testEmptyLineInTheEnd.txt")
        val testCommand = UnknownCommand("wc testData/testEmptyLineInTheEnd.txt")

        checkEqualWcOutput(testCommand, command)
    }

    @Test
    internal fun testWcPipe() {
        val command = WcCommand(null)
        val echoCommand = EchoCommand(listOf("1"))

        val out = ByteArrayOutputStream()

        val runner = CommandRunner(State(), out)

        runner.process(echoCommand, Delimeter.PIPE)
        runner.process(command, Delimeter.EOL)

        checkEqualWcOutput("1 1 1", out.toString())
    }

    @Test
    internal fun testWcEmpty() {
        val command = WcCommand("testData/testEmpty.txt")
        val testCommand = UnknownCommand("wc testData/testEmpty.txt")

        checkEqualWcOutput(testCommand, command)
    }

    @Test
    internal fun testNoSuchFile() {
        val command = WcCommand("whoosh.hrhrhr")

        val out = ByteArrayOutputStream()
        val runner = CommandRunner(State(), out)


        assertThat({ runner.process(command, Delimeter.EOL) }, throws<ShellRuntimeException>())
    }

    @Test
    internal fun testNotEnoughParams() {
        val command = WcCommand(null)

        val out = ByteArrayOutputStream()
        val runner = CommandRunner(State(), out)

        assertThat({ runner.process(command, Delimeter.EOL) }, throws<ShellUsageException>())
    }

    fun checkEqualWcOutput(expected: Command, actual: Command) {
        val out = ByteArrayOutputStream()
        val testOut = ByteArrayOutputStream()

        val runner = CommandRunner(State(), out)
        val testRunner = CommandRunner(State(), testOut)

        runner.process(actual, Delimeter.EOL)
        testRunner.process(expected, Delimeter.EOL)

        checkEqualWcOutput(testOut.toString(), out.toString())
    }

    fun checkEqualWcOutput(expected: String, actual: String) {

        fun toInts(s: String) = s.trim().split(Regex("\\s+")).take(3).map(String::toInt)

        Assert.assertEquals(toInts(expected), toInts(actual))
    }
}