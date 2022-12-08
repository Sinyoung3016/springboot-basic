package org.prgrms.kdt.forward.io;

import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class ConsoleOutput implements Output {

    public void write(String string) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));) {
            writer.write(string + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}