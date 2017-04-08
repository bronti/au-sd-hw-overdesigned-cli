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
}