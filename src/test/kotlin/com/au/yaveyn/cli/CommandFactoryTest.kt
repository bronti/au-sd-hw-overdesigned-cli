package com.au.yaveyn.cli

import com.au.yaveyn.cli.commands.*
import com.au.yaveyn.cli.exceptions.ShellUsageException
import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.throws
import org.junit.Test
import java.io.ByteArrayInputStream
import com.natpryce.hamkrest.Matcher

/**
 * Created by bronti on 08.04.17.
 */

fun Command.hasName(s: String) = this.name == s
fun Command.filePathIs(s: String) = if (this is CatCommand) this.filePath == s else if (this is WcCommand) this.filePath == s else false
fun Command.filePathEmpty() = if (this is CatCommand) this.filePath == null else if (this is WcCommand) this.filePath == null else false
fun Command.paramsAre(ls: List<String>) = if (this is EchoCommand) this.params == ls else  false
fun Command.unknownCommandIs(s: String) = if (this is UnknownCommand) this.command == s else  false

class CommandFactoryTest {

    val hasName = Matcher(Command::hasName)
    val filePathIs = Matcher(Command::filePathIs)
    val filePathEmpty = Matcher(Command::filePathEmpty)
    val paramsAre = Matcher(Command::paramsAre)
    val unknownCommandIs = Matcher(Command::unknownCommandIs)

    @Test
    internal fun testCatCommand() {
        val command = "cat"
        val emptyParam = emptyList<String>()
        val validParam = listOf("cat")
        val invalidParam = listOf("cat", "cat")
        val factory = CommandFactory()

        assertThat(factory.constructCommand(command, emptyParam), hasName("cat") and filePathEmpty)
        assertThat(factory.constructCommand(command, validParam), hasName("cat") and filePathIs("cat"))
        assertThat({ factory.constructCommand(command, invalidParam) }, throws<ShellUsageException>())
    }

    @Test
    internal fun testEchoCommand() {
        val command = "echo"
        val params = listOf("Hi there")
        val factory = CommandFactory()

        assertThat(factory.constructCommand(command, params), hasName("echo") and paramsAre(params))
    }

    @Test
    internal fun testExitCommand() {
        val command = "exit"
        val emptyParam = emptyList<String>()
        val invalidParam = listOf("cat", "cat")
        val factory = CommandFactory()

        assertThat(factory.constructCommand(command, emptyParam), hasName("exit"))
        assertThat({ factory.constructCommand(command, invalidParam) }, throws<ShellUsageException>())
    }

    @Test
    internal fun testPwdCommand() {
        val command = "pwd"
        val emptyParam = emptyList<String>()
        val invalidParam = listOf("cat", "cat")
        val factory = CommandFactory()

        assertThat(factory.constructCommand(command, emptyParam), hasName("pwd"))
        assertThat({ factory.constructCommand(command, invalidParam) }, throws<ShellUsageException>())
    }

    @Test
    internal fun testUnknownCommand() {
        val command = "unknown"
        val emptyParam = emptyList<String>()
        val validParam = listOf("cat", "cat")
        val factory = CommandFactory()

        assertThat(factory.constructCommand(command, emptyParam), hasName("unknown command") and unknownCommandIs("unknown"))
        assertThat(factory.constructCommand(command, validParam), hasName("unknown command") and unknownCommandIs("unknown cat cat"))
    }

    @Test
    internal fun testWcCommand() {
        val command = "wc"
        val emptyParam = emptyList<String>()
        val validParam = listOf("cat")
        val invalidParam = listOf("cat", "cat")
        val factory = CommandFactory()

        assertThat(factory.constructCommand(command, emptyParam), hasName("wc") and filePathEmpty)
        assertThat(factory.constructCommand(command, validParam), hasName("wc") and filePathIs("cat"))
        assertThat({ factory.constructCommand(command, invalidParam) }, throws<ShellUsageException>())
    }
}