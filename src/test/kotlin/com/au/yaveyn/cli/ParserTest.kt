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
        val c : ShellRunnable = StateChange("aaa", "715!")
        Assert.assertEquals(p.parse("aaa=715!"), c)
    }

    @Test
    fun testZeroParams() {
        val p = Parser()
        Assert.assertEquals(p.parse("exit"), ExitCommand())
    }

    @Test
    fun testOneParam() {
        val p = Parser()
        Assert.assertEquals(p.parse("wc www"), WcCommand("www"))
    }

    @Test
    fun testUnknownCommand() {
        val p = Parser()
        Assert.assertEquals(p.parse("wcl www"), UnknownCommand("wcl www"))
    }
}