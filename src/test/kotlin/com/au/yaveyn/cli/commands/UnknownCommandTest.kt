package com.au.yaveyn.cli.commands

import com.au.yaveyn.cli.*
import com.au.yaveyn.cli.exceptions.ShellRuntimeException
import com.au.yaveyn.cli.exceptions.ShellUsageException
import com.natpryce.hamkrest.*
import com.natpryce.hamkrest.assertion.assertThat
import org.junit.Assert
import org.junit.Test
import java.io.ByteArrayOutputStream

/**
 * Created by bronti on 08.04.17.
 */

class UnknownCommandTest {

    @Test
    internal fun testRuntimeError() {
        val command = UnknownCommand("exit 1")

        val out = ByteArrayOutputStream()
        val runner = CommandRunner(State(), out)

        assertThat({ runner.process(command, Delimeter.EOL) }, throws<ShellRuntimeException>())
    }

    @Test
    internal fun testSimple() {
        val command = UnknownCommand("echo 1")

        val out = ByteArrayOutputStream()
        val runner = CommandRunner(State(), out)
        runner.process(command, Delimeter.EOL)

        assertThat(out.toString(), equalTo("1\n"))
    }
}