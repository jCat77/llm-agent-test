package com.jcat.domain;


public interface LLMService {

    String generate(String userMessage);

    String generate(String userMessage, String userId);
}
