package com.au.yaveyn.cli

import com.au.yaveyn.cli.exceptions.ShellUsageException
import org.junit.Test
import java.io.ByteArrayInputStream
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.Matcher
import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.throws

/**
 * Created by bronti on 08.04.17.
 */

fun Pair<String, Delimeter>?.resultEndsWithPipe(): Boolean = if (this != null) this.second == Delimeter.PIPE else false
fun Pair<String, Delimeter>?.resultEndsWithEOL(): Boolean = if (this != null) this.second == Delimeter.EOL else false
fun Pair<String, Delimeter>?.resultIsEmpty(): Boolean = this == null

fun Pair<String, Delimeter>?.resultCommandIs(s: String): Boolean = if (this != null) this.first == s else false

class CommandReaderTest {

    val endsWithPipe = Matcher(Pair<String, Delimeter>?::resultEndsWithPipe)
    val endsWithEOL = Matcher(Pair<String, Delimeter>?::resultEndsWithEOL)
    val commandIs = Matcher(Pair<String, Delimeter>?::resultCommandIs)
    val isEmpty = Matcher(Pair<String, Delimeter>?::resultIsEmpty)

    @Test
    internal fun testOneCommand() {
        val input = "exit"
        val reader = CommandReader(ByteArrayInputStream(input.toByteArray(charset("UTF-8"))), Preprocessor(State()))

        assertThat(reader.getCommand(), endsWithEOL and commandIs("exit"))
        assertThat(reader.getCommand(), isEmpty)
    }

    @Test
    internal fun testPipe() {
        val input = "exit | echo wow"
        val reader = CommandReader(ByteArrayInputStream(input.toByteArray(charset("UTF-8"))), Preprocessor(State()))

        assertThat(reader.getCommand(), endsWithPipe and commandIs("exit"))
        assertThat(reader.getCommand(), endsWithEOL and commandIs("echo wow"))
        assertThat(reader.getCommand(), isEmpty)
    }

    @Test
    internal fun testEmptyCommand() {
        val input = " | "
        val reader = CommandReader(ByteArrayInputStream(input.toByteArray(charset("UTF-8"))), Preprocessor(State()))

        assertThat({ reader.getCommand() }, throws<ShellUsageException>())
    }
}
