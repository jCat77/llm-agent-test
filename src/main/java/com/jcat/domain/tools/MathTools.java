package com.jcat.domain.tools;


import com.jcat.domain.tools.annotation.ToolMeta;
import com.jcat.domain.tools.parser.MathToolParameterParser;

public class MathTools implements Tool {

    @dev.langchain4j.agent.tool.Tool("For add or summ two values, use it method. Используй этот метод, чтобы сложить 2 значения")
    @ToolMeta(MathToolParameterParser.class)
    double add(double a, double b) {
        return a + b;
    }

    @dev.langchain4j.agent.tool.Tool("For multiply two values, use it method. Используй этот метод, чтобы перемножить 2 значения")
    @ToolMeta(MathToolParameterParser.class)
    double multiply(double a, double b) {
        return a * b;
    }

    public static class Params {
        double a;
        double b;
    }
}
