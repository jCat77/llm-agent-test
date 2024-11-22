package com.jcat.domain.tools;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jcat.domain.tools.annotation.ToolMeta;
import com.jcat.domain.tools.parser.MathToolParameterParser;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

public class MathTools implements Tool {

    @dev.langchain4j.agent.tool.Tool("For add or summ two or more values, use it method. Используй этот метод, чтобы сложить 2 или больше значения")
    @ToolMeta(MathToolParameterParser.class)
    public double add(Object operands) {

        Objects.requireNonNull(operands);
        List<Double> typedOperands = ((Params) operands).getOperands();

        double result = 0;
        for (Double operand : typedOperands) {
            if (operand != null) {
                result += operand;
            }
        }
        return result;
    }

    @dev.langchain4j.agent.tool.Tool("For multiply two or more values, use it method. Используй этот метод, чтобы перемножить 2 или более значений")
    @ToolMeta(MathToolParameterParser.class)
    public double multiply(Object operands) {

        Objects.requireNonNull(operands);
        List<Double> typedOperands = ((Params) operands).getOperands();

        double result = 1;
        for (Double operand : typedOperands) {
            if (operand != null) {
                result *= operand;
            }
        }
        return result;
    }


    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Params {
        private List<Double> operands;
    }
}
