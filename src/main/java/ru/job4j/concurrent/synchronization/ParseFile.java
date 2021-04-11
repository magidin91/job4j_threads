package ru.job4j.concurrent.synchronization;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized String content(Predicate<Character> filter) throws IOException {
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            StringBuilder output = new StringBuilder();
            int data;
            while ((data = i.read()) > 0) {
                if (filter.test((char) data)) {
                    output.append(data);
                }
            }
            return output.toString();
        }
    }

    public String getContentWithoutUnicode() throws IOException {
        return content(character -> character < 0x80);
    }

    public String getContent() throws IOException {
        return content(character -> true);
    }
}