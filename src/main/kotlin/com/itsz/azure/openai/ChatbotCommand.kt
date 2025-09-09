package com.itsz.azure.openai

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.model.ChatModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import java.util.concurrent.Callable
import java.util.concurrent.CountDownLatch

@Component
@Command(name = "chatbot", aliases = ["chatbot"])
class ChatbotCommand(chatModel: ChatModel) : Callable<Int> {
    private val chatClient = ChatClient.create(chatModel)

    @Option(
        names = ["--prompt", "-p"],
        description = ["The prompt to send to the chatbot"],
        required = false
    )
    @Value("\${prompt:Hello, how are you?}")
    private lateinit var prompt: String

    override fun call(): Int {
        val latchCountDown = CountDownLatch(1)
        var exitCode = 0
        chatClient.prompt().user(prompt).stream().content()
            .doOnError { exitCode = -1 }
            .doOnComplete {
                println()
                latchCountDown.countDown()
            }
            .subscribe {
                print(it)
            }
        latchCountDown.await()
        return exitCode
    }
}