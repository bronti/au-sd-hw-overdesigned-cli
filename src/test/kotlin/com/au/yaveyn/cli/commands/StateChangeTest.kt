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

class StateChangeTest {

    @Test
    internal fun testSimple() {
        val command = StateChange("a", "var_a")

        val out = ByteArrayOutputStream()
        val state = State()
        val runner = CommandRunner(state, out)
        runner.process(command, Delimeter.EOL)

        assertThat(state.getVar("a"), equalTo("var_a"))
    }

    @Test
    internal fun testAlreadyExistent() {
        val command = StateChange("a", "var_a'")

        val out = ByteArrayOutputStream()
        val state = State()
        state.setVar("a", "var_a")
        val runner = CommandRunner(state, out)
        runner.process(command, Delimeter.EOL)

        assertThat(state.getVar("a"), equalTo("var_a'"))
    }
}