package com.au.yaveyn.cli

/**
 * Preprocess given string by resolving all variables.
 */
class Preprocessor (val state: State) {

    private enum class InputState {
        BASE,
        VAR,
        QUOTE
    }

    private val varEnd = Regex("\\s")

    /**
     * Runs preprocessing on given string.
     */
    fun preprocess(str: String): String {

        val result = StringBuilder()
        val varName = StringBuilder()
        var inputState = InputState.BASE

        var reuseCurrentChar = false
        var currentChar = ' '
        var currentInd = 0
        while (reuseCurrentChar || currentInd < str.length) {
            if (!reuseCurrentChar) {
                currentChar = str[currentInd]
                currentInd++
            }
            else {
                reuseCurrentChar = false
            }
            inputState = when (Pair(inputState, currentChar)) {
                Pair(InputState.BASE, '$') -> InputState.VAR
                Pair(InputState.BASE, '\'') -> {
                    result.append("\'")
                    InputState.QUOTE
                }
                Pair(InputState.QUOTE, '\'') -> {
                    result.append("\'")
                    InputState.BASE
                }
                Pair(InputState.VAR, currentChar) -> {
                    if (charFitsIntoVar(currentChar)) {
                        varName.append(currentChar)
                        InputState.VAR
                    } else {
                        result.append(state.getVar(varName.toString()))
                        varName.setLength(0)
                        reuseCurrentChar = true
                        InputState.BASE
                    }
                }
                else -> {
                    result.append(currentChar)
                    inputState
                }
            }
        }

        if (varName.isNotEmpty()) {
            result.append(state.getVar(varName.toString()))
        }

        return result.toString()
    }
}