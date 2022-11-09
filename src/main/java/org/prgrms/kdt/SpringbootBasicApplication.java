package org.prgrms.kdt;

import org.prgrms.kdt.forward.IOController;
import org.prgrms.kdt.forward.io.ConsoleInput;
import org.prgrms.kdt.forward.io.ConsoleOutput;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

@SpringBootApplication
public class SpringbootBasicApplication implements CommandLineRunner {

    private final IOController ioController;

    public SpringbootBasicApplication(IOController ioController) {
        this.ioController = ioController;
    }

    public static void main(String[] args) {
        SpringApplication.run(IOController.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ioController.run();
    }
}
