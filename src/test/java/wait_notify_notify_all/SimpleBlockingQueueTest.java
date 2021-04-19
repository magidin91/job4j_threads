package wait_notify_notify_all;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SimpleBlockingQueueTest {

    @Test
    public void when3OfferAnd3Poll() throws InterruptedException {
        SimpleBlockingQueue<Integer> blockingQueue = new SimpleBlockingQueue<>();

        Thread producer = new Thread(() ->
        {
            int i = 0;
            while (i < 3) {
                blockingQueue.offer(1);
                i++;
            }
        });

        Thread consumer = new Thread(() ->
        {
            int i = 0;
            while (i < 3) {
                try {
                    blockingQueue.poll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                i++;
            }
        });

        producer.start();
        consumer.start();

        consumer.join();

        assertThat(blockingQueue.size(), is(0));
    }
}