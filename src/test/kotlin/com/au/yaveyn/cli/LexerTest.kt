package com.au.yaveyn.cli

import com.au.yaveyn.cli.exceptions.ShellUsageException
import com.sun.javafx.fxml.expression.Expression
import org.junit.Assert
import org.junit.Test

class LexerTest {

    @Test
    fun testOneWord() {
        val lex = Lexer()
        Assert.assertEquals(
                listOf(Lexer.Token(Lexer.TokenType.WORD, "aaa!&&*&*^#")),
                lex.tokenize("aaa!&&*&*^#"))
    }

    @Test
    fun testSeveralWords() {
        val lex = Lexer()
        Assert.assertEquals(
                listOf(Lexer.Token(Lexer.TokenType.WORD, "aaa!&&*&*^#"),
                        Lexer.Token(Lexer.TokenType.WORD, "edvs"),
                        Lexer.Token(Lexer.TokenType.WORD, "eswf")),
                lex.tokenize("aaa!&&*&*^# edvs eswf"))
    }

    @Test
    fun testAssignment() {
        val lex = Lexer()
        Assert.assertEquals(
                listOf(Lexer.Token(Lexer.TokenType.WORD, "aaa"),
                        Lexer.Token(Lexer.TokenType.ASSIGNMENT, ""),
                        Lexer.Token(Lexer.TokenType.WORD, "*&*^#")),
                lex.tokenize("aaa=*&*^#"))
    }

    @Test
    fun testQuotes() {
        val lex = Lexer()
        Assert.assertEquals(
                listOf(Lexer.Token(Lexer.TokenType.WORD, "The Doctor in the TARDIS")),
                lex.tokenize("'The Doctor in the TARDIS'"))
    }

    @Test
    fun testDoubleQuotes() {
        val lex = Lexer()
        Assert.assertEquals(
                listOf(Lexer.Token(Lexer.TokenType.WORD, "The Doctor in the TARDIS")),
                lex.tokenize("\"The Doctor in the TARDIS\""))
    }

    @Test(expected = ShellUsageException::class)
    fun testInvalidName() {
        val lex = Lexer()
        lex.tokenize("aa!a=*&*^#")
    }

    @Test(expected = ShellUsageException::class)
    fun testDoubleAssignment() {
        val lex = Lexer()
        lex.tokenize("aaa=*&*^=#")
    }

    @Test(expected = ShellUsageException::class)
    fun testParamsAfterAssignment() {
        val lex = Lexer()
        lex.tokenize("aaa!&&=*&*^# ekejbv d")
    }

    @Test(expected = ShellUsageException::class)
    fun testMismatchedQuote() {
        val lex = Lexer()
        lex.tokenize("'ouch")
    }

    @Test(expected = ShellUsageException::class)
    fun testMismatchedDoubleQuote() {
        val lex = Lexer()
        lex.tokenize("\"ouch")
    }
}