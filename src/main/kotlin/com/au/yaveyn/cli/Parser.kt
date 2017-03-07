package com.au.yaveyn.cli

import com.au.yaveyn.cli.commands.ShellRunnable
import com.au.yaveyn.cli.commands.StateChange
import com.sun.javaws.exceptions.InvalidArgumentException


/**
 * Parses given command and creates a ShellRunnable object from it.
 */
class Parser {

    private val lexer = Lexer()
    private val factory = CommandFactory()

    /**
     * Runs parser on given string.
     */
    fun parse(str: String): ShellRunnable {
        val lexems = lexer.tokenize(str)

        if (lexems.size == 3
                && lexems[0].type == Lexer.TokenType.WORD
                && lexems[1].type == Lexer.TokenType.ASSIGNMENT
                && lexems[2].type == Lexer.TokenType.WORD) {
            return StateChange(lexems[0].value, lexems[2].value)
        }
        if (lexems.isNotEmpty() && lexems.all { it.type == Lexer.TokenType.WORD }) {
            val command = lexems[0].value
            val param_values = lexems.drop(1).map { it.value }
            return factory.constructCommand(command, param_values)
        }
        //todo: exception
        throw InvalidArgumentException(arrayOf("Error: invalid command"))
    }
}