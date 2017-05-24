package com.au.yaveyn.cli.commands

import com.au.yaveyn.cli.*
import com.au.yaveyn.cli.exceptions.ShellRuntimeException
import com.au.yaveyn.cli.exceptions.ShellUsageException
import com.natpryce.hamkrest.Matcher
import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.throws
import org.junit.Assert
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.FileInputStream

/**
 * Created by bronti on 08.04.17.
 */

class EchoCommandTest {

    @Test
    internal fun testSimple() {
        val params = listOf("1", "!...", "\n", "spam")
        val command = EchoCommand(params)
        val text = params.joinToString(" ")

        val out = ByteArrayOutputStream()
        val runner = CommandRunner(State(), out)
        runner.process(command, Delimeter.EOL)

        assertThat(out.toString(), equalTo(text + "\n"))
    }

    @Test
    internal fun testPipe() {
        val command = EchoCommand(listOf("2"))
        val echoCommand = EchoCommand(listOf("1"))

        val out = ByteArrayOutputStream()

        val runner = CommandRunner(State(), out)

        runner.process(echoCommand, Delimeter.PIPE)
        runner.process(command, Delimeter.EOL)

        assertThat(out.toString(), equalTo("2\n"))
    }

    @Test
    internal fun testEmpty() {
        val command = EchoCommand(emptyList())

        val out = ByteArrayOutputStream()
        val runner = CommandRunner(State(), out)
        runner.process(command, Delimeter.EOL)

        assertThat(out.toString(), equalTo("\n"))
    }
}