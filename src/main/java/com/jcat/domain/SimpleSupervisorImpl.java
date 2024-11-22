package com.jcat.domain;

import com.jcat.properties.LLMProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleSupervisorImpl implements Supervisor {

    @Autowired
    private LLMService llmService;

    @Autowired
    private LLMProperties llmProperties;

    @Override
    public String userMessage(String userMessage) {
        try {
            return llmService.generate(userMessage);
        } catch (Exception e) {
            //ignored
            return llmProperties.getFuckupMessage();
        }
    }
}
