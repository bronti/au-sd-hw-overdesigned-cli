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

class PwdCommandTest {

    @Test
    internal fun testSimple() {
        val command = PwdCommand()
        val testCommand = UnknownCommand("pwd")
        val out = ByteArrayOutputStream()
        val testOut = ByteArrayOutputStream()

        val runner = CommandRunner(State(), out)
        val testRunner = CommandRunner(State(), testOut)

        runner.process(command, Delimeter.EOL)
        testRunner.process(testCommand, Delimeter.EOL)

        Assert.assertEquals(out.toString(), testOut.toString())
    }


}