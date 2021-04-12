package ru.job4j.concurrent.synchronization;

import java.io.*;

public class WriteFile {
    private final File file;

    public WriteFile(File file) {
        this.file = file;
    }

    public synchronized void saveContent(String content) throws IOException {
        try (PrintWriter out = new PrintWriter(file)) {
            out.print(content);
        }
    }
}
