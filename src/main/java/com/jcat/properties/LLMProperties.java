package com.jcat.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Component
@ConfigurationProperties("com.jcat.model.llm")
@Validated
@Getter
@Setter
@ToString
public class LLMProperties {

    @NotEmpty
    String fuckupMessage;

    @Valid
    private OpenAi openAi = new OpenAi();

    @Getter
    @Setter
    public static class OpenAi {

        @NotEmpty
        private String apiKey;

        @NotEmpty
        private String modelName;

        @Valid
        private Context context = new Context();

        private SystemPrompt systemPrompt = new SystemPrompt();


        @Getter
        @Setter
        public static class Context {

            @NotEmpty
            private int length = 16 * 1024;

        }
    }

    @Getter
    @Setter
    public static class SystemPrompt {

        @NotEmpty
        private String defaultPrompt;

    }
}
