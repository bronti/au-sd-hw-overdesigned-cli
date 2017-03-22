package com.au.yaveyn.cli

import com.au.yaveyn.cli.commands.EchoCommand
import org.junit.Assert
import org.junit.Test
import java.io.ByteArrayOutputStream

class CommandRunnerTest {

    @Test
    fun testEol() {
        val c = EchoCommand(listOf("ololo"))
        val s = ByteArrayOutputStream()
        val r = CommandRunner(State(), s)
        r.process(c, Delimeter.EOL)
        Assert.assertEquals(s.toString(), "ololo\n")
    }

    @Test
    fun testPipe() {
        val c1 = EchoCommand(listOf("ololo"))
        val c2 = EchoCommand(listOf("oxxxy rulezz"))
        val s = ByteArrayOutputStream()
        val r = CommandRunner(State(), s)
        r.process(c1, Delimeter.PIPE)
        r.process(c2, Delimeter.EOL)

        Assert.assertEquals(s.toString(), "oxxxy rulezz\n")
    }
}