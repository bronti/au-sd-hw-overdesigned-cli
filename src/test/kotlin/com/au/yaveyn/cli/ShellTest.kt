package com.au.yaveyn.cli

import com.au.yaveyn.cli.exceptions.ShellUsageException
import com.natpryce.hamkrest.*
import org.junit.Test
import java.io.ByteArrayInputStream
import com.natpryce.hamkrest.assertion.assertThat
import java.io.ByteArrayOutputStream

/**
 * Created by bronti on 08.04.17.
 */


class ShellTest {

    @Test
    internal fun testEmptyOutput() {
        val inputText = "exit"
        val input = ByteArrayInputStream(inputText.toByteArray(charset("UTF-8")))

        val output = ByteArrayOutputStream()

        val shell = Shell(input, output)
        shell.run()

        assertThat(output.toString().trim(), isEmptyString)
    }

    @Test
    internal fun testSimple() {
        val inputText = "echo 123\nexit"
        val input = ByteArrayInputStream(inputText.toByteArray(charset("UTF-8")))

        val output = ByteArrayOutputStream()

        val shell = Shell(input, output)
        shell.run()

        assertThat(output.toString().trim(), equalTo("123"))
    }

    @Test
    internal fun testUsageError() {
        val inputText = "cat\n"
        val input = ByteArrayInputStream(inputText.toByteArray(charset("UTF-8")))

        val output = ByteArrayOutputStream()

        val shell = Shell(input, output)
        shell.run()

        assertThat(output.toString(), containsSubstring("invalid usage"))
    }

    @Test
    internal fun testRuntimeError() {
        val inputText = "cat ololo\nexin"
        val input = ByteArrayInputStream(inputText.toByteArray(charset("UTF-8")))

        val output = ByteArrayOutputStream()

        val shell = Shell(input, output)
        shell.run()

        assertThat(output.toString(), containsSubstring("runtime exception"))
    }
}
