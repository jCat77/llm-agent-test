package com.jcat;

import com.jcat.domain.LLMService;
import com.jcat.misc.Color;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Scanner;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {

    private final LLMService llmService;

    public Application(LLMService llmService) {
        this.llmService = llmService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args).close();
    }

    @Override
    public void run(String... args) throws IOException {
        System.out.println(Color.WHITE + "Для выхода набери выход!" + Color.RESET);

        while (true) {
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();
            if ("выход".equals(userInput)) {
                break;
            }
            String reply = llmService.generate(userInput);
            System.out.println(Color.CYAN_BRIGHT + reply + Color.RESET);
        }
        System.out.println(Color.WHITE + "Пока!" + Color.RESET);

    }

}
