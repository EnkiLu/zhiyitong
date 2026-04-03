package com.aimedical.java.ai.langchain4j.controller;

import com.aimedical.java.ai.langchain4j.assistant.ZhiyitongAgent;
import com.aimedical.java.ai.langchain4j.bean.ChatForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Tag(name = "硅谷小智")
@RestController
@RequestMapping("/zhiyitong")
public class ZhiyitongController {
    @Autowired
    private ZhiyitongAgent zhiyitongAgent;
    @Operation(summary = "对话")
    @PostMapping(value = "/chat", produces = "text/stream;charset=utf-8")
    public Flux<String> chat(@RequestBody ChatForm chatForm) {
        return zhiyitongAgent.chat(chatForm.getMemoryId(), chatForm.getMessage());
    }
}
