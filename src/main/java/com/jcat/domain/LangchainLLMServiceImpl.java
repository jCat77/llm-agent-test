package com.jcat.domain;

import com.jcat.domain.tools.ToolMeta;
import com.jcat.domain.tools.ToolProvider;
import com.jcat.domain.tools.parser.ParameterParser;
import com.jcat.properties.LLMProperties;
import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.data.message.*;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Slf4j
@RequiredArgsConstructor
public class LangchainLLMServiceImpl implements LLMService, ApplicationContextAware {

    private static final String DEFAULT_USER_ID = "default";

    private final ChatLanguageModel chatLanguageModel;

    private final List<ToolSpecification> toolSpecifications;

    private final ExecutorService executorService;

    private ApplicationContext applicationContext;

    private final ToolProvider toolProvider;

    private final Map<String, ChatMemory> memoryMap;

    private final LLMProperties llmProperties;

    @Override
    public String generate(String userMessage) {
        return generate(userMessage, DEFAULT_USER_ID);
    }

    @Override
    public String generate(String text, String userId) {
        //try read from memory
//        ChatMemory chatMemory = memoryMap.get(userId);
//        if (chatMemory == null) {
//            chatMemory = applicationContext.getBean(ChatMemory.class);
//            memoryMap.put(userId, chatMemory);
//        }

//        List<ChatMessage> messages = chatMemory.messages();
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(SystemMessage.from(llmProperties.getOpenAi().getSystemPrompt().getDefaultPrompt()));
        UserMessage newMessage = UserMessage.from(text);

        Set<ChatMessage> newMessages = new HashSet<>();
        newMessages.add(newMessage);

        try {

            messages.add(newMessage);
            Response<AiMessage> response = chatLanguageModel.generate(messages, toolSpecifications);

            AiMessage aiMessage = response.content();
            newMessages.add(aiMessage);

            //if has tool execution request
            if (aiMessage.hasToolExecutionRequests()) {
                messages.add(aiMessage);

                List<Future<ToolExecutionResultMessage>> futures = new ArrayList<>();

                for (ToolExecutionRequest toolExecutionRequest : aiMessage.toolExecutionRequests()) {
                    log.info("Tool has been requested by LLM={}", toolExecutionRequest);
                    futures.add(executorService.submit(new ToolCaller(toolExecutionRequest, toolProvider)));
                }

                for (Future<ToolExecutionResultMessage> f : futures) {
                    try {
                        ToolExecutionResultMessage toolExecutionResultMessage = f.get();
                        messages.add(toolExecutionResultMessage);
                        newMessages.add(toolExecutionResultMessage);
                    } catch (Exception e) {
                        log.error("Exception when call tool", e);
                    }
                }
                return chatLanguageModel.generate(messages).content().text();
            }

            log.info("No tool has been requested for text={}", text);
            return aiMessage.text();

        } catch (Exception e) {
            log.error("Some error while handle message={} by user={}", text, userId);
            throw e;

        } finally {
            try {
//                newMessages.forEach(chatMemory::add);
            } catch (Exception e) {
                log.error("Error when store new messages", e);
            }
        }
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @RequiredArgsConstructor
    public class ToolCaller implements Callable<ToolExecutionResultMessage> {

        private final ToolExecutionRequest toolExecutionRequest;
        private final ToolProvider toolProvider;

        @Override
        public ToolExecutionResultMessage call() throws InvocationTargetException, IllegalAccessException {

            //find tool for execute by id
            ToolMeta toolMeta = toolProvider.get(toolExecutionRequest.name());
            log.info("Found tool={} for request={}", toolMeta, toolExecutionRequest);

            //Try to call method with params
            com.jcat.domain.tools.annotation.ToolMeta annotation = toolMeta.method().getAnnotation(com.jcat.domain.tools.annotation.ToolMeta.class);
            Class<?> parameterParserClazz = annotation.value();
            ParameterParser<?> parameterParser = (ParameterParser<?>) applicationContext.getBean(parameterParserClazz);
            Object params = parameterParser.parse(toolExecutionRequest.arguments());
            Object result = toolMeta.method().invoke(toolMeta.tool(), params);

            return ToolExecutionResultMessage.from(toolExecutionRequest, result.toString());
        }
    }
}
