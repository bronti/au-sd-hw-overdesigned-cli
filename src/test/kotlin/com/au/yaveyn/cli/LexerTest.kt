package com.au.yaveyn.cli

import com.au.yaveyn.cli.exceptions.ShellUsageException
import com.sun.javafx.fxml.expression.Expression
import org.junit.Assert
import org.junit.Test

class LexerTest {

    @Test
    fun testOneWord() {
        val lex = Lexer()
        Assert.assertEquals(lex.tokenize("aaa!&&*&*^#"),
                listOf(Lexer.Token(Lexer.TokenType.WORD, "aaa!&&*&*^#")))
    }

    @Test
    fun testSeveralWords() {
        val lex = Lexer()
        Assert.assertEquals(lex.tokenize("aaa!&&*&*^# edvs eswf"),
                listOf(Lexer.Token(Lexer.TokenType.WORD, "aaa!&&*&*^#"),
                        Lexer.Token(Lexer.TokenType.WORD, "edvs"),
                        Lexer.Token(Lexer.TokenType.WORD, "eswf")))
    }

    @Test
    fun testAssignment() {
        val lex = Lexer()
        Assert.assertEquals(lex.tokenize("aaa=*&*^#"),
                listOf(Lexer.Token(Lexer.TokenType.WORD, "aaa"),
                        Lexer.Token(Lexer.TokenType.ASSIGNMENT, ""),
                        Lexer.Token(Lexer.TokenType.WORD, "*&*^#")))
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
}