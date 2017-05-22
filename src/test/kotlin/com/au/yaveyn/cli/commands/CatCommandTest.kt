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

class CatCommandTest {

    @Test
    internal fun testSimple() {
        val filePath = "testData/test.txt"
        val command = CatCommand(filePath)
        val text = FileInputStream(filePath).bufferedReader().readText()

        val out = ByteArrayOutputStream()
        val runner = CommandRunner(State(), out)
        runner.process(command, Delimeter.EOL)

        assertThat(out.toString(), equalTo(text + "\n"))
    }

    @Test
    internal fun testNoSuchFile() {
        val command = CatCommand("whoosh.hrhrhr")

        val out = ByteArrayOutputStream()
        val runner = CommandRunner(State(), out)

        assertThat({ runner.process(command, Delimeter.EOL) }, throws<ShellRuntimeException>())
    }

    @Test
    internal fun testPipe() {
        val command = CatCommand(null)
        val echoCommand = EchoCommand(listOf("1"))

        val out = ByteArrayOutputStream()

        val runner = CommandRunner(State(), out)

        runner.process(echoCommand, Delimeter.PIPE)
        runner.process(command, Delimeter.EOL)

        assertThat(out.toString(), equalTo("1\n"))
    }

    @Test
    internal fun testEmpty() {
        val command = CatCommand("testData/testEmpty.txt")

        val out = ByteArrayOutputStream()
        val runner = CommandRunner(State(), out)
        runner.process(command, Delimeter.EOL)

        assertThat(out.toString(), equalTo("\n"))
    }

    @Test
    internal fun testNotEnoughParams() {
        val command = CatCommand(null)

        val out = ByteArrayOutputStream()
        val runner = CommandRunner(State(), out)

        assertThat({ runner.process(command, Delimeter.EOL) }, throws<ShellUsageException>())
    }
}