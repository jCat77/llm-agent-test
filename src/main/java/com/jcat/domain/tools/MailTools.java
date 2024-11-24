package com.jcat.domain.tools;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jcat.domain.tools.annotation.ToolMeta;
import com.jcat.domain.tools.parser.MailToolParameterParser;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

public class MailTools implements Tool {

    @dev.langchain4j.agent.tool.Tool("For sending mail use this method. Используй этот метод, чтобы отправить почту.")
    @ToolMeta(MailToolParameterParser.class)
    public String sendMail(Params operands) {

        Objects.requireNonNull(operands);
        Params.Operands typedOperands = operands.getOperands();

        return "Mail successfully send to {" + typedOperands.getAddress() + "} with text{" + typedOperands.getText() + "}";
    }



    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Params {

        private Operands operands;

        @Data
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Operands {
            private String address;
            private String text;
        }

    }

}
