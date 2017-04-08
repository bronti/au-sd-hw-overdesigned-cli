package com.au.yaveyn.cli

import com.au.yaveyn.cli.commands.*
import com.au.yaveyn.cli.exceptions.ShellUsageException
import com.sun.org.apache.bcel.internal.classfile.Unknown
import org.junit.Assert
import org.junit.Test

class ParserTest {

    @Test
    fun testStateChanger() {
        val p = Parser()
        val result : ShellRunnable = StateChange("aaa", "715!")
        Assert.assertEquals(result, p.parse("aaa=715!"))
    }

    @Test
    fun testZeroParams() {
        val p = Parser()
        Assert.assertEquals(ExitCommand(), p.parse("exit"))
    }

    @Test
    fun testOneParam() {
        val p = Parser()
        Assert.assertEquals(WcCommand("www"), p.parse("wc www"))
    }

    @Test
    fun testUnknownCommand() {
        val p = Parser()
        Assert.assertEquals(UnknownCommand("wcl www"), p.parse("wcl www"))
    }
}