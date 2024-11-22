package com.jcat.configuration;

import com.jcat.domain.LLMService;
import com.jcat.domain.LangchainLLMServiceImpl;
import com.jcat.domain.tools.Tool;
import com.jcat.domain.tools.ToolProvider;
import com.jcat.properties.LLMProperties;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.agent.tool.ToolSpecifications;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.Tokenizer;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@Configuration
public class LangChainConfiguration {

    @Bean
    public ChatLanguageModel getOpenAIModel(LLMProperties llmProperties) {
        return OpenAiChatModel.builder()
                .modelName(llmProperties.getOpenAi().getModelName())
                .apiKey(llmProperties.getOpenAi().getApiKey())
                .build();
    }

    @Bean
    public LLMService getLLmService(ChatLanguageModel chatLanguageModel,
                                    @Qualifier("llmServiceExecutor") ExecutorService executorService,
                                    List<ToolSpecification> toolSpecificationList,
                                    ToolProvider toolProvider,
                                    Map<String, ChatMemory> memoryMap) {
        return new LangchainLLMServiceImpl(chatLanguageModel, toolSpecificationList, executorService, toolProvider, memoryMap);
    }

    @Bean("defaultChatMemory")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ChatMemory chatMemory(ChatMemoryStore chatMemoryStore, Tokenizer tokenizer, LLMProperties llmProperties) {
        return new TokenWindowChatMemory.Builder()
                .chatMemoryStore(chatMemoryStore)
                .maxTokens(llmProperties.getOpenAi().getContext().getLength(), tokenizer)
                .build();
    }

    @Bean
    public Map<String, ChatMemory> memoryMap() {
        return new HashMap<>();
    }

    @Bean
    public ChatMemoryStore chatMemoryStore() {
        return new InMemoryChatMemoryStore();
    }

    @Bean
    public Tokenizer tokenizer() {
        return new OpenAiTokenizer();
    }


    @Bean
    public List<ToolSpecification> toolSpecifications(Tool... tools) {
        List<ToolSpecification> toolSpecifications = new ArrayList<>(tools.length);
        for (Tool t : tools) {
            toolSpecifications.addAll(ToolSpecifications.toolSpecificationsFrom(t.getClass()));
        }
        return toolSpecifications;
    }


}
