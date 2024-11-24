package com.jcat.domain.tools.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcat.domain.tools.MailTools;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MailToolParameterParser implements ParameterParser<MailTools.Params> {

    private final ObjectMapper objectMapper;

    @Override
    public MailTools.Params parse(String params) {
        try {
            return objectMapper.readValue(params, MailTools.Params.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
