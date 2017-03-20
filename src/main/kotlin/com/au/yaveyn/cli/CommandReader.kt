package com.au.yaveyn.cli

import com.sun.javaws.exceptions.InvalidArgumentException
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

private val delimiter = Regex(delimPattern)
private val delimiterWithSpaces = Regex("\\s*" + delimPattern + "\\s*")
private val command = Regex(commandPattern)

private val endedByDelim = Regex("$commandPattern*$delimPattern")
private fun isDelimiterWithSpaces(delim: String) = delimiterWithSpaces.matchEntire(delim) != null

/**
 * Class for handling input pipes and splitting it to separate commands.
 */
class CommandReader(val input: BufferedReader, val preprocessor: Preprocessor) : AutoCloseable {

    constructor(input: InputStream, preprocessor: Preprocessor): this(BufferedReader(InputStreamReader(input)), preprocessor)

    var commandBuffer: List<Pair<String, Delimeter>> = emptyList()

    private fun checkedFeed() {
        while (commandBuffer.isEmpty()) {

            val line = (input.readLine()?: return) + "\n"
            if (line.isBlank()) continue

            val splittedLine = endedByDelim.findAll(line)
                    .toList()
                    .map { it.value }

            splittedLine
                    .forEach { if (isDelimiterWithSpaces(it)) throw InvalidArgumentException(arrayOf("Syntax error near unexpected delimiter: " + it)) }
            commandBuffer = splittedLine
                    .map { Pair(command.find(it)!!.value.trim(), delimeterInstance(delimiter.find(it)!!.value)) }
                    .toList()
        }
    }

    /**
     * Gets next command from the input.
     */
    fun getCommand(): Pair<String, Delimeter>? {
        if (hasNext()) {
            val (rawCommand, delim) = commandBuffer[0]
            commandBuffer = commandBuffer.drop(1)
            return Pair(preprocessor.preprocess(rawCommand), delim)
        } else {
            return null
        }
    }

    /**
     * Checks whether the input is empty.
     */
    fun hasNext(): Boolean {
        checkedFeed()
        return ! commandBuffer.isEmpty()
    }

    /**
     * Closes input.
     */
    override fun close() = input.close()
}