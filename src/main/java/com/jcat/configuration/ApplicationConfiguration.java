package com.jcat.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcat.domain.tools.*;
import com.jcat.domain.tools.parser.MailToolParameterParser;
import com.jcat.domain.tools.parser.MathToolParameterParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Configuration
@Slf4j
public class ApplicationConfiguration {

    @Bean
    public MathTools mathTools() {
        return new MathTools();
    }

    @Bean
    public MailTools mailTools() {
        return new MailTools();
    }


    @Bean
    @Qualifier("llmServiceExecutor")
    public ExecutorService executorService(@Qualifier("llmServiceExecutorTf") ThreadFactory threadFactory) {
        return Executors.newCachedThreadPool(threadFactory);
    }

    @Bean
    @Qualifier("llmServiceExecutorTf")
    public ThreadFactory threadFactory() {
        return r -> {
            ThreadGroup tg = new ThreadGroup(Thread.currentThread().getThreadGroup(), "llmServicePool");
            return new Thread(tg, r);
        };
    }

    @Bean
    public Map<String, ToolMeta> getTools(Tool... tools) {
        Map<String, ToolMeta> m = new HashMap<>();
        for (Tool t : tools) {
            for (Method method : t.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(dev.langchain4j.agent.tool.Tool.class)) {
                    if (!method.isAnnotationPresent(com.jcat.domain.tools.annotation.ToolMeta.class)) {
                        log.error("Ignore tool={}, cause not ToolMeta annotation found on class={}, method={}", method.getAnnotation(dev.langchain4j.agent.tool.Tool.class).name(), t.getClass().getName(), method.getName());
                    }
                    log.info("tool={} initialized successfully class={}, method={}", method.getAnnotation(dev.langchain4j.agent.tool.Tool.class).name(), t.getClass().getName(), method.getName());
                    m.put(method.getName(), ToolMeta.create(t, method));
                }
            }
        }
        return m;
    }

    @Bean
    public ToolProvider getToolProvider(Map<String, ToolMeta> toolMap) {
        return new MapBasedToolProvider(toolMap);
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public MathToolParameterParser getMathToolParameterParser(ObjectMapper objectMapper) {
        return new MathToolParameterParser(objectMapper);
    }

    @Bean
    public MailToolParameterParser getMailToolParameterParser(ObjectMapper objectMapper) {
        return new MailToolParameterParser(objectMapper);
    }

}
