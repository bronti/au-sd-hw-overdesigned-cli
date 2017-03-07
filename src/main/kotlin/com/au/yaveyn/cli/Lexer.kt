package com.au.yaveyn.cli

import com.sun.javaws.exceptions.InvalidArgumentException
import sun.plugin.dom.exception.InvalidStateException

/**
 * Splits input string and creates list of tokens out of it.
 */
class Lexer {

    /**
     * Types of Tokens.
     */
    enum class TokenType {
        WORD,
        ASSIGNMENT
    }

    /**
     * Token class.
     */
    data class Token(val type: TokenType, val value: String)

    private val word = Regex("(\\S+)\\s*")
    private val assign = Regex("=")

    private fun isAssignment(str: String) = str.contains(assign)

    private enum class InputState {
        BASE,
        WHITESPACES,
        ONE_QUOTE,
        TWO_QUOTE
    }

    /**
     * Tokenizes input string.
     */
    fun tokenize(str: String): List<Token> {
        val command = str.trim()
        val firstWordMatch = word.find(command) ?: throw InvalidStateException("Empty command.")
        val firstWord = firstWordMatch.groupValues[1]
        if (isAssignment(str)) {
            //todo: ex
            val tail = command.removeRange(firstWordMatch.range).trim()
            if (tail.isNotEmpty()) throw InvalidArgumentException(arrayOf("Invalid usage of assignment: no parameters are required."))
            val assignment = assign.split(firstWord)
            if (assignment.size != 2) throw InvalidArgumentException(arrayOf("Invalid usage of assignment: there should be exactly one '=' in assignment."))
            if (!assignment[0].all(::charFitsIntoVar)) throw InvalidArgumentException(arrayOf("Invalid usage of assignment: variable name should contain [a-zA-z0-9_] only."))
            return listOf(Token(TokenType.WORD, assignment[0]), Token(TokenType.ASSIGNMENT, ""), Token(TokenType.WORD, assignment[1]))
        } else {
            return tokenizeCommand(command)
        }
    }

    private fun tokenizeCommand(allParams: String): List<Token> {

        val currentToken = StringBuilder()
        val result: MutableList<Token> = mutableListOf()
        var inputState = InputState.BASE

        fun saveCurrentToken() {
            result.add(Token(TokenType.WORD, currentToken.toString()))
            currentToken.setLength(0)
        }

        for (currentChar in allParams) {
            inputState = when (Pair(inputState, currentChar)) {
                Pair(InputState.BASE, '\'') -> {
                    InputState.ONE_QUOTE
                }
                Pair(InputState.BASE, '\"') -> {
                    InputState.TWO_QUOTE
                }
                Pair(InputState.BASE, currentChar) -> {
                    if (currentChar.isWhitespace()) {
                        saveCurrentToken()
                        InputState.WHITESPACES
                    } else {
                        currentToken.append(currentChar)
                        InputState.BASE
                    }
                }
                Pair(InputState.WHITESPACES, '\'') -> {
                    InputState.ONE_QUOTE
                }
                Pair(InputState.WHITESPACES, '\"') -> {
                    InputState.TWO_QUOTE
                }
                Pair(InputState.WHITESPACES, currentChar) -> {
                    if (currentChar.isWhitespace()) {
                        InputState.WHITESPACES
                    } else {
                        currentToken.append(currentChar)
                        InputState.BASE
                    }
                }
                Pair(InputState.ONE_QUOTE, '\'') -> {
                    InputState.BASE
                }
                Pair(InputState.ONE_QUOTE, currentChar) -> {
                    currentToken.append(currentChar)
                    InputState.ONE_QUOTE
                }
                Pair(InputState.TWO_QUOTE, '\"') -> {
                    InputState.BASE
                }
                Pair(InputState.TWO_QUOTE, currentChar) -> {
                    currentToken.append(currentChar)
                    InputState.TWO_QUOTE
                }
                else -> throw InvalidStateException("Invalid automaton state.")
            }
        }
        if (inputState == InputState.ONE_QUOTE || inputState == InputState.TWO_QUOTE) {
            //todo: ex
            throw InvalidArgumentException(arrayOf("Invalid command: all quotes should be closed."))
        }
        if (inputState == InputState.BASE) {
            saveCurrentToken()
        }
        return result
    }
}