package ru.job4j.concurrent.threads;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Скачивает с выбранной скоростью файл по указанному URL
 */
public class Wget implements Runnable {
    private final String url;
    private final long timeExpected;
    private static final String FILENAME  = "src/main/resources/downloaded_file.txt";

    public Wget(String url, int speed) {
        this.url = url;
        timeExpected = 1000 / speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(FILENAME)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead; // кол-во прочитанных байт
            long start = System.currentTimeMillis();

            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);

                long time = System.currentTimeMillis() - start;
                long difference = timeExpected - time;
                if (difference > 0) {
                    Thread.sleep(difference);
                }
                start = System.currentTimeMillis();
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]); // kB/s
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}