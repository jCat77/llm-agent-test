package com.jcat.domain.tools;


import com.jcat.domain.tools.annotation.ToolMeta;
import com.jcat.domain.tools.parser.MailToolParameterParser;

public class MailTools implements Tool {

    @dev.langchain4j.agent.tool.Tool("For sending mail use this method. Используй этот метод, чтобы отправить почту.")
    @ToolMeta(MailToolParameterParser.class)
    String sendMail(String address, String text) {
        return "Mail successfully send to {" + address + "} with text{" + text + "}";
    }

    public static class Params {
        String address;
        String text;
    }

}
