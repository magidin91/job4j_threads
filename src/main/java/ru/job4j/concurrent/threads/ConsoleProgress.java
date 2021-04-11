package ru.job4j.concurrent.threads;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        char[] process = {'\\', '|', '/'};
        int index = 0;

        while (!Thread.currentThread().isInterrupted()) {
            System.out.print("\r load: " + process[index++]);

            index = (index == process.length)? 0 : index;

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000); /* симулируем выполнение параллельной задачи в течение 5 секунд */
        progress.interrupt();
    }
}
