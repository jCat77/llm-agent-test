package com.jcat.domain.tools.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcat.domain.tools.MathTools;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MathToolParameterParser implements ParameterParser<MathTools.Params> {

    private final ObjectMapper objectMapper;

    @Override
    public MathTools.Params parse(String params) {
        try {
            return objectMapper.readValue(params, MathTools.Params.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
