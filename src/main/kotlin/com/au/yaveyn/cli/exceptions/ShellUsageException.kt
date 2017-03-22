package com.au.yaveyn.cli.exceptions

/**
 * Exception for invalid commands.
 */
internal class ShellUsageException(message: String) : RuntimeException("invalid usage: $message")
