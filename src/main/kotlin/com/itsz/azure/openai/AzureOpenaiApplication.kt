package com.itsz.azure.openai

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.ExitCodeGenerator
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import picocli.CommandLine

@SpringBootApplication
class AzureOpenaiApplication(val command: ChatbotCommand): ExitCodeGenerator, CommandLineRunner{
	private var exitCode: Int = 0

	override fun run(vararg args: String?) {
		exitCode = CommandLine(command).execute(*args)
	}

	override fun getExitCode(): Int {
		return exitCode
	}
}

fun main(args: Array<String>) {
	runApplication<AzureOpenaiApplication>(*args)
}
