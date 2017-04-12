package com.au.yaveyn.cli.exceptions

/**
 * Exception for runtime error in command.
 */
class ShellRuntimeException(message: String, cause: Exception) : RuntimeException("runtime exception: $message", cause)