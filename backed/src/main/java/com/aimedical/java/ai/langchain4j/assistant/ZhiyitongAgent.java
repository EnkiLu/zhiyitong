package com.aimedical.java.ai.langchain4j.assistant;
import dev.langchain4j.service.*;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;
@AiService(
    wiringMode = EXPLICIT,
    streamingChatModel = "qwenStreamingChatModel",
    chatMemoryProvider = "chatMemoryProviderZhiyitong",
    tools = "appointmentTools",
    contentRetriever = "contentRetrieverZhiyitongPincone" //配置向量存储
)
public interface ZhiyitongAgent {
    @SystemMessage(fromResource = "Zhiyitong-prompt-template.txt")
    Flux<String> chat(@MemoryId Long memoryId, @UserMessage String userMessage);
}
