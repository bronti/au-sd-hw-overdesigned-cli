package com.au.yaveyn.cli.commands

import com.au.yaveyn.cli.State
import com.au.yaveyn.cli.exceptions.ShellUsageException
import com.au.yaveyn.cli.streams.CommandInputStream
import com.au.yaveyn.cli.streams.CommandOutputStream
import org.apache.commons.cli.*


/**
 * Grep command implementation.
 */
class GrepCommand(val args: List<String>) : Command() {

    companion object {
        /**
         * Maximum number of parameters.
         */
        val maxNumberOfParams: Int? = 1

        private val options = Options()
        init {
            options.addOption("i", false, "case insensitivity")
            options.addOption("w", false, "whole word")
            val optionA = Option
                    .builder("A")
                    .numberOfArgs(1)
                    .argName("number")
                    .desc("print <number> of strings after a match")
                    .build()
            options.addOption(optionA)
        }
    }

    override val name = "grep"

    private val cmd = parseArguments()
    init {
        if (cmd.argList.isEmpty()) throw ShellUsageException("$name: pattern needed")
        if (cmd.argList.size > 2) throw ShellUsageException("$name: too many arguments")
    }
    private val pattern = if (cmd.isWholeWord()) "\\b${cmd.argList[0]}\\b" else cmd.argList[0]
    private val regex = if (cmd.isCaseInSensitive()) Regex(pattern, RegexOption.IGNORE_CASE) else Regex(pattern)

    private val filePath = if (cmd.argList.size == 1) null else cmd.argList[1]
    private val linesToPrintAfterMatch: Int
        get() {
            if (!cmd.shouldPrintAfterMatch()) {
                return 0
            }
            else try {
                return cmd.getNumberOfLinesAfterMatchToPrint().toInt()
            }
            catch (ex: NumberFormatException) {
                throw ShellUsageException("$name: -A option requires integer parameter")
            }
        }

    override fun run(state: State, input: CommandInputStream?, output: CommandOutputStream) {
        val inputLines = getInputReader(filePath, input).readLines()
        inputLines.foldRight(0) { line, linesToPrint ->
            val matched = regex.containsMatchIn(line)
            if (matched || linesToPrint > 0) output.writeln(line)
            when {
                matched -> linesToPrintAfterMatch
                linesToPrint == 0 -> 0
                else -> linesToPrint - 1
            }
        }
    }

    private fun parseArguments(): CommandLine {
        val parser: CommandLineParser = DefaultParser()
        return try {
            parser.parse(options, args.toTypedArray())
        } catch (exp: ParseException) {
            throw ShellUsageException("$name: " + exp.message)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as GrepCommand

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}

fun CommandLine.isCaseInSensitive() = this.hasOption("i")
fun CommandLine.isWholeWord() = this.hasOption("w")
fun CommandLine.shouldPrintAfterMatch() = this.hasOption("A")
fun CommandLine.getNumberOfLinesAfterMatchToPrint() = this.getOptionValue("A")
