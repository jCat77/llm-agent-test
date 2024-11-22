package com.jcat;

import com.jcat.domain.Supervisor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Scanner;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {

    private final Supervisor supervisor;

    public Application(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws IOException {
        log.info("Application started....");
        log.info("Type quit to exit.");

        while (true) {
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();
            if ("quit".equals(userInput)) {
                break;
            }
            String reply = supervisor.userMessage(userInput);
            System.out.println("Server:" + reply);
        }
        log.info("Bye!");
    }
}
