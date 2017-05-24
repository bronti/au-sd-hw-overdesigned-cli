package com.au.yaveyn.cli.commands

import com.au.yaveyn.cli.CommandRunner
import com.au.yaveyn.cli.Delimeter
import com.au.yaveyn.cli.State
import com.au.yaveyn.cli.exceptions.ShellRuntimeException
import com.au.yaveyn.cli.exceptions.ShellUsageException
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.throws
import org.junit.Rule
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.FileInputStream

class GrepCommandTest {

    fun makeParams(params: List<String>, pattern: String) = params + listOf(pattern, "./testData/LoremIpsum.txt")

    @Test
    internal fun testParamA() {
        val params = listOf("-i", "-A", "1")
        val pattern = "integER"
        val command = GrepCommand(makeParams(params, pattern))

        val out = ByteArrayOutputStream()
        val runner = CommandRunner(State(), out)
        runner.process(command, Delimeter.EOL)

        assertThat(out.toString().trim().lines().size, equalTo(6))
    }

    @Test
    internal fun testCaseInSensitivity() {
        val params = listOf("-i")
        val pattern = "integer"
        val command = GrepCommand(makeParams(params, pattern))

        val out = ByteArrayOutputStream()
        val runner = CommandRunner(State(), out)
        runner.process(command, Delimeter.EOL)

        assertThat(out.toString().trim().lines().size, equalTo(3))
    }

    @Test
    internal fun testWholeWord() {
        val params = listOf("-i", "-w")
        val pattern = "id"
        val command = GrepCommand(makeParams(params, pattern))

        val out = ByteArrayOutputStream()
        val runner = CommandRunner(State(), out)
        runner.process(command, Delimeter.EOL)

        assertThat(out.toString().trim().lines().size, equalTo(6))
    }

    @Test
    internal fun testCaseSensitivity() {
        val params = emptyList<String>()
        val pattern = "integer"
        val command = GrepCommand(makeParams(params, pattern))

        val out = ByteArrayOutputStream()
        val runner = CommandRunner(State(), out)
        runner.process(command, Delimeter.EOL)

        assertThat(out.toString().trim(), equalTo(""))
    }
}