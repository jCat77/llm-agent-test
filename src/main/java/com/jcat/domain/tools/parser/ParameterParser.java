package com.jcat.domain.tools.parser;

public interface ParameterParser<T> {

    T parse(String params);
}
